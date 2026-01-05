import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./Navbar";
import ProtectedRoute from "./ProtectedRoute";
import LoginPage from "./LoginPage";
import RegisterPage from "./RegisterPage";
import SalesSummary from "./SalesSummary";
import AddCashTracker from "./AddCashTracker";
import UploadExcel from "./UploadExcel";
import LotteryInventoryTracker from "./LotteryInventoryTracker";
import LotteryInventoryTrackerSuccessPage from "./LotteryInventoryTrackerSuccessPage";
import Products from "./Products";

const App = () => {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      
      {/* Protected routes */}
      <Route
        path="/*"
        element={
          <ProtectedRoute>
            <>
              <Navbar />
              <div className="container mt-4">
                <Routes>
                  <Route path="/" element={<SalesSummary />} />
                  <Route path="/sales-summary" element={<SalesSummary />} />
                  <Route path="/addCash" element={<AddCashTracker />} />
                  <Route path="/uploadExcel" element={<UploadExcel />} />
                  <Route path="/lottery" element={<LotteryInventoryTracker />} />
                  <Route path="/lottery/:date/success" element={<LotteryInventoryTrackerSuccessPage />} />
                  <Route path="/products" element={<Products/>} />
                  <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
              </div>
            </>
          </ProtectedRoute>
        }
      />
    </Routes>
  );
};

export default App;
