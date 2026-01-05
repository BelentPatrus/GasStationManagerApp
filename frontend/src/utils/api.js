import { API_BASE_URL } from "../config/api";


let isRefreshing = false;
let refreshSubscribers = [];

const subscribeTokenRefresh = (callback) => {
  refreshSubscribers.push(callback);
};

const onTokenRefreshed = (token) => {
  refreshSubscribers.forEach(callback => callback(token));
  refreshSubscribers = [];
};

const refreshAccessToken = async () => {
  try {
    const response = await fetch(`/api/auth/refresh`, {
      method: 'POST',
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Token refresh failed');
    }

    const data = await response.json();
    localStorage.setItem('accessToken', data.accessToken);
    return data.accessToken;
  } catch (error) {
    localStorage.removeItem('accessToken');
    window.location.href = '/login';
    throw error;
  }
};

export const apiCall = async (endpoint, options = {}) => {
  const token = localStorage.getItem('accessToken');
  
  const defaultHeaders = {
    'Content-Type': 'application/json',
  };

  if (token) {
    defaultHeaders['Authorization'] = `Bearer ${token}`;
  }

  const headers = {
    ...defaultHeaders,
    ...options.headers,
  };

  let response = await fetch(`${endpoint}`, {
    ...options,
    headers,
    credentials: 'include',
  });

  if (response.status === 401 && token) {
    if (!isRefreshing) {
      isRefreshing = true;

      try {
        const newToken = await refreshAccessToken();
        isRefreshing = false;
        onTokenRefreshed(newToken);

        headers['Authorization'] = `Bearer ${newToken}`;
        response = await fetch(`${endpoint}`, {
          ...options,
          headers,
          credentials: 'include',
        });
      } catch (error) {
        isRefreshing = false;
        throw error;
      }
    } else {
      const newToken = await new Promise((resolve) => {
        subscribeTokenRefresh((token) => {
          resolve(token);
        });
      });

      headers['Authorization'] = `Bearer ${newToken}`;
      response = await fetch(`${endpoint}`, {
        ...options,
        headers,
        credentials: 'include',
      });
    }
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: 'Request failed' }));
    throw new Error(error.message || `HTTP ${response.status}`);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
};

export const api = {
  get: (endpoint, options = {}) => 
    apiCall(endpoint, { ...options, method: 'GET' }),

  post: (endpoint, data, options = {}) =>
    apiCall(endpoint, {
      ...options,
      method: 'POST',
      body: JSON.stringify(data),
    }),

  put: (endpoint, data, options = {}) =>
    apiCall(endpoint, {
      ...options,
      method: 'PUT',
      body: JSON.stringify(data),
    }),

  delete: (endpoint, options = {}) =>
    apiCall(endpoint, { ...options, method: 'DELETE' }),

  postForm: (endpoint, formData, options = {}) =>
  apiCall(endpoint, {
    ...options,
    method: 'POST',
    body: formData,
    headers: {}, // let browser set boundary
  }),

};