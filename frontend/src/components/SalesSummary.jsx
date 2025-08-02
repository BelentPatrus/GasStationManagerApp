import React, { useState, useEffect } from "react";
import axios from "axios";
import DepartmentAndCategorySales from "./DepartmentAndCategorySales";
import DepartmentSalesChart from "./DepartmentSalesChart";

const SalesSummary = () => {
  const [date, setDate] = useState("");
  const [salesData, setSalesData] = useState(null);
  const [noData, setNoData] = useState(false);
  const [isItemizedVisible, setIsItemizedVisible] = useState(false);
  const [isDepartmentVisible, setIsDepartmentVisible] = useState(false);
  const [isCategoryVisible, setIsCategoryVisible] = useState(false);
  const [showChart, setShowChart] = useState(false);
  
  // New state for calendar functionality
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth());
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [availableDates, setAvailableDates] = useState([]);
  const [isCalendarVisible, setIsCalendarVisible] = useState(false);
  const [isLoadingDates, setIsLoadingDates] = useState(false);

  // Fetch available dates for the current month/year
  const fetchAvailableDates = async (year, month) => {
    setIsLoadingDates(true);
    try {
      // Create a date string for the first day of the month (YYYY-MM-01)
      const monthStr = String(month + 1).padStart(2, '0');
      const dateStr = `${year}-${monthStr}-01`;
      
      const response = await axios.get(`http://localhost:8080/consumer/all/${dateStr}`);
      setAvailableDates(response.data || []);
    } catch (error) {
      console.error("Error fetching available dates:", error);
      setAvailableDates([]);
    } finally {
      setIsLoadingDates(false);
    }
  };

  // Fetch available dates when month/year changes
  useEffect(() => {
    if (isCalendarVisible) {
      fetchAvailableDates(currentYear, currentMonth);
    }
  }, [currentMonth, currentYear, isCalendarVisible]);

  const fetchSalesData = async () => {
    if (!date) return;
    try {
      const response = await axios.get(`http://localhost:8080/consumer/${date}`);
      if (response.data && response.data.merchandiseItemSales.length > 0) {
        setSalesData(response.data);
        setNoData(false);
      } else {
        setSalesData(null);
        setNoData(true);
      }
    } catch (error) {
      console.error("Error fetching sales data:", error);
      setSalesData(null);
      setNoData(true);
    }
  };

  // Calendar helper functions
  const getDaysInMonth = (year, month) => {
    return new Date(year, month + 1, 0).getDate();
  };

  const getFirstDayOfMonth = (year, month) => {
    return new Date(year, month, 1).getDay();
  };

  const isDateAvailable = (dateStr) => {
    return availableDates.includes(dateStr);
  };

  const formatDateString = (year, month, day) => {
    const monthStr = String(month + 1).padStart(2, '0');
    const dayStr = String(day).padStart(2, '0');
    return `${year}-${monthStr}-${dayStr}`;
  };

  const handleDateSelect = (selectedDate) => {
    setDate(selectedDate);
    setIsCalendarVisible(false);
  };

  const handleMonthChange = (direction) => {
    if (direction === 'prev') {
      if (currentMonth === 0) {
        setCurrentMonth(11);
        setCurrentYear(currentYear - 1);
      } else {
        setCurrentMonth(currentMonth - 1);
      }
    } else {
      if (currentMonth === 11) {
        setCurrentMonth(0);
        setCurrentYear(currentYear + 1);
      } else {
        setCurrentMonth(currentMonth + 1);
      }
    }
  };

  const renderCalendar = () => {
    const daysInMonth = getDaysInMonth(currentYear, currentMonth);
    const firstDay = getFirstDayOfMonth(currentYear, currentMonth);
    const monthNames = [
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];
    const dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    const days = [];
    
    // Empty cells for days before the first day of the month
    for (let i = 0; i < firstDay; i++) {
      days.push(<div key={`empty-${i}`} className="calendar-day empty"></div>);
    }

    // Days of the month
    for (let day = 1; day <= daysInMonth; day++) {
      const dateStr = formatDateString(currentYear, currentMonth, day);
      const isAvailable = isDateAvailable(dateStr);
      const isSelected = date === dateStr;

      days.push(
        <div
          key={day}
          className={`calendar-day ${isAvailable ? 'available' : ''} ${isSelected ? 'selected' : ''}`}
          onClick={() => isAvailable && handleDateSelect(dateStr)}
          style={{ cursor: isAvailable ? 'pointer' : 'default' }}
        >
          {day}
        </div>
      );
    }

    return (
      <div className="calendar-container" style={{ position: 'relative', marginBottom: '20px' }}>
        <div className="calendar-header d-flex justify-content-between align-items-center mb-3">
          <button 
            className="btn btn-sm btn-outline-primary"
            onClick={() => handleMonthChange('prev')}
          >
            ‹
          </button>
          <h5 className="mb-0">{monthNames[currentMonth]} {currentYear}</h5>
          <button 
            className="btn btn-sm btn-outline-primary"
            onClick={() => handleMonthChange('next')}
          >
            ›
          </button>
        </div>
        
        <div className="calendar-grid" style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(7, 1fr)',
          gap: '5px',
          maxWidth: '350px'
        }}>
          {dayNames.map(day => (
            <div key={day} className="calendar-day-header text-center fw-bold" style={{ padding: '8px' }}>
              {day}
            </div>
          ))}
          {days}
        </div>
        
        {isLoadingDates && (
          <div className="text-center mt-2">
            <small className="text-muted">Loading available dates...</small>
          </div>
        )}
        
        <div className="mt-2">
          <small className="text-muted">
            <span className="badge bg-success me-2">●</span>
            Available dates ({availableDates.length})
          </small>
        </div>
      </div>
    );
  };

  return (
    <div className="container mt-4">
      <div className="card">
        <div className="card-body">
          <h2 className="card-title mb-4">Daily Sales Summary</h2>
          
          {/* Date Selection Section */}
          <div className="mb-3">
            <div className="d-flex gap-2 align-items-center mb-2">
              <input
                type="date"
                value={date}
                onChange={(e) => setDate(e.target.value)}
                className="form-control"
                style={{ maxWidth: '200px' }}
              />
              <span className="text-muted">or</span>
              <button 
                className="btn btn-outline-primary"
                onClick={() => {
                  setIsCalendarVisible(!isCalendarVisible);
                  if (!isCalendarVisible) {
                    fetchAvailableDates(currentYear, currentMonth);
                  }
                }}
              >
                {isCalendarVisible ? 'Hide Calendar' : 'Show Calendar'}
              </button>
            </div>
            
            {isCalendarVisible && (
              <div className="border rounded p-3 bg-light">
                {renderCalendar()}
              </div>
            )}
          </div>

          <button className="btn btn-primary mb-3" onClick={fetchSalesData}>
            Fetch Sales
          </button>
          
          {noData && <p className="text-danger">No log for this date, contact administration.</p>}
          
          {/* Collapsible Sales Section Buttons */}
          <div className="d-flex flex-wrap gap-2 mt-3">
            <button
              className="btn btn-primary"
              onClick={() => setIsItemizedVisible(!isItemizedVisible)}
            >
              {isItemizedVisible ? "Hide Itemized Sales" : "Show Itemized Sales"}
            </button>

            <button
              className="btn btn-primary"
              onClick={() => setIsDepartmentVisible(!isDepartmentVisible)}
            >
              {isDepartmentVisible ? "Hide Department Sales" : "Show Department Sales"}
            </button>

            <button
              className="btn btn-primary"
              onClick={() => setIsCategoryVisible(!isCategoryVisible)}
            >
              {isCategoryVisible ? "Hide Product Category Sales" : "Show Product Category Sales"}
            </button>
            
            <button
              className="btn btn-primary"
              onClick={() => setShowChart(!showChart)}
            >
              {showChart ? "Hide Pie Chart Sales" : "Show Pie Chart Sales"}
            </button>
          </div>

          {salesData && (
            <div>
              <h3 className="mt-4">Total Sales: ${salesData.totalExtendedRetail.toFixed(2)}</h3>
              <h3>Total Items Sold: {salesData.totalQuantitySold}</h3>
              
              {isItemizedVisible && (
                <div className="table-responsive mt-3">
                  <table className="table table-bordered table-striped">
                    <thead className="thead-dark">
                      <tr>
                        <th scope="col">Department</th>
                        <th scope="col">Product</th>
                        <th scope="col" className="text-center">Qty Sold</th>
                        <th scope="col" className="text-center">Unit Price</th>
                        <th scope="col" className="text-center">Total</th>
                      </tr>
                    </thead>
                    <tbody>
                      {salesData.merchandiseItemSales.map((item, index) => (
                        <tr key={index}>
                          <td>{item.department}</td>
                          <td>{item.description}</td>
                          <td className="text-center">{item.quantitySold}</td>
                          <td className="text-center">${item.unitRetail.toFixed(2)}</td>
                          <td className="text-center font-weight-bold">${item.extendedRetail.toFixed(2)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}

              <DepartmentSalesChart departmentSales={showChart ? salesData.departmentSales : null}/>
              <DepartmentAndCategorySales
                departmentSales={isDepartmentVisible ? salesData.departmentSales : null}
                productCategorySales={isCategoryVisible ? salesData.productCategorySales : null}
              />
            </div>
          )}
        </div>
      </div>

      <style jsx>{`
        .calendar-day {
          padding: 8px;
          text-align: center;
          border: 1px solid #dee2e6;
          background-color: #f8f9fa;
          border-radius: 3px;
          min-height: 35px;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        
        .calendar-day.empty {
          border: none;
          background-color: transparent;
        }
        
        .calendar-day.available {
          background-color: #d4edda;
          border-color: #c3e6cb;
          font-weight: bold;
        }
        
        .calendar-day.available:hover {
          background-color: #c3e6cb;
        }
        
        .calendar-day.selected {
          background-color: #0d6efd;
          color: white;
          border-color: #0d6efd;
        }
        
        .calendar-day-header {
          background-color: #e9ecef;
          border: 1px solid #dee2e6;
          border-radius: 3px;
        }
      `}</style>
    </div>
  );
};

export default SalesSummary;