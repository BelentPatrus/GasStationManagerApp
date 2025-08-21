import React, { useEffect, useState } from "react";
import { useRef } from "react";
import EditModal from "./productcomponents/EditModal.jsx";

/**
 * BasicProductsTable — minimal, nice-looking Bootstrap table
 * Columns: upc, description, brand, department, productCategory, packageDescription, baseCost, currentStock
 *
 * Works with either:
 *  - Array response:   GET <API> -> [{...product}]
 *  - Pageable response: GET <API> -> { content: [{...product}], ... }
 *
 * TODO: Set API_PRODUCTS to your backend route (e.g.,
 *   "/consumer/products" or "/api/products").
 */

const API_PRODUCTS = `/product/`; // <-- change to your real endpoint

function parseJsonOrThrow(res) {
  return res.text().then((text) => {
    try {
      const data = text ? JSON.parse(text) : null;
      if (!res.ok)
        throw new Error(data?.message || data?.error || res.statusText);
      return data;
    } catch (e) {
      if (e instanceof SyntaxError) {
        // backend returned HTML (e.g., 404 page/login)
        const snippet = (text || "").slice(0, 240);
        throw new Error(snippet || `Non-JSON response (HTTP ${res.status})`);
      }
      throw e;
    }
  });
}

export default function Products() {
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [q, setQ] = useState("");
  const modalRef = useRef(null);

  const filteredRows = React.useMemo(() => {
    const needle = q.trim().toLowerCase();
    if (!needle) return rows;
    const text = (v) =>
      (v == null
        ? ""
        : typeof v === "string"
        ? v
        : v.name ?? String(v)
      ).toLowerCase();

    return rows.filter((p) =>
      [
        p.upc,
        p.description,
        p.brand,
        p.department, // string or { name }
        p.productCategory, // string or { name }
        p.packageDescription,
        p.baseCost,
        p.currentStock,
      ]
        .map(text)
        .some((t) => t.includes(needle))
    );
  }, [rows, q]);

  async function load() {
    try {
      setLoading(true);
      setError("");
      const res = await fetch(API_PRODUCTS, {
        headers: { Accept: "application/json" },
        credentials: "include",
      });
      const data = await parseJsonOrThrow(res);
      const items = Array.isArray(data) ? data : data?.content || [];
      setRows(items);
    } catch (e) {
      setError(e.message || "Failed to load products");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    load();
  }, []);

  return (
    <div className="container my-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h1 className="h4 m-0">Products</h1>
        <button
          className="btn btn-outline-secondary"
          onClick={load}
          disabled={loading}
        >
          Refresh
        </button>
      </div>
      <div className="card shadow-sm">
        <div className="table-responsive" style={{ overflowX: "auto" }}>
          <div className="mb-3 d-flex">
            <input
              className="form-control"
              placeholder="Search UPC, description, brand…"
              value={q}
              onChange={(e) => setQ(e.target.value)} // live filter
            />
          </div>
          <table
            className="table table-striped table-hover align-middle mb-0"
            style={{ minWidth: 1100 }} // tweak this number to whatever feels right
          >
            <thead
              className="table-light"
              style={{ position: "sticky", top: 0, zIndex: 1 }}
            >
              <tr>
                <th scope="col">UPC</th>
                <th scope="col">Description</th>
                <th scope="col">Brand</th>
                <th scope="col">Department</th>
                <th scope="col">Category</th>
                <th scope="col">Package</th>
                <th scope="col" className="text-end">
                  Base Cost
                </th>
                <th scope="col" className="text-end">
                  Retail Price
                </th>
                <th scope="col" className="text-end">
                  Profit Margin %
                </th>
                <th scope="col" className="text-end">
                  Stock
                </th>
                <th className="text-end">Actions</th>
              </tr>
            </thead>
            <tbody>
              {loading && (
                <tr>
                  <td colSpan={8} className="p-4 text-center text-muted">
                    Loading…
                  </td>
                </tr>
              )}

              {!loading && error && (
                <tr>
                  <td colSpan={8} className="p-4 text-center text-danger small">
                    {error}
                  </td>
                </tr>
              )}

              {!loading && !error && rows.length === 0 && (
                <tr>
                  <td colSpan={8} className="p-4 text-center text-muted">
                    No products found.
                  </td>
                </tr>
              )}

              {!loading &&
                !error &&
                !loading &&
                !error &&
                filteredRows.map((p) => (
                  <tr key={p.upc}>
                    <td className="font-monospace">{p.upc}</td>
                    <td>{p.description}</td>
                    <td>{p.brand}</td>
                    <td>
                      {(p?.department && (p.department.name || p.department)) ||
                        ""}
                    </td>
                    <td>
                      {(p?.productCategory &&
                        (p.productCategory.name || p.productCategory)) ||
                        ""}
                    </td>
                    <td>{p.packageDescription}</td>
                    <td className="text-end">
                      {p.baseCost != null
                        ? `$${Number(p.baseCost).toFixed(2)}`
                        : "—"}
                    </td>
                    <td className="text-end">{0}</td>
                    <td className="text-end">{0}</td>
                    <td className="text-end">{p.currentStock ?? "—"}</td>
                    <td className="text-end">
                      <button
                        onClick={() => {
                          console.log("row at edit:", p);
                          modalRef.current?.open(p);
                        }}
                      >
                        Edit
                      </button>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>
      // render the modal once near the bottom of Products.jsx:
      <EditModal
        ref={modalRef}
        onSaved={(updated, oldUPC) => {
          setRows((prev) => {
            const i = prev.findIndex((p) => p.upc === oldUPC);
            if (i === -1) return prev;
            const copy = [...prev];
            copy[i] = updated;
            return copy;
          });
        }}
      />
    </div>
  );
}
