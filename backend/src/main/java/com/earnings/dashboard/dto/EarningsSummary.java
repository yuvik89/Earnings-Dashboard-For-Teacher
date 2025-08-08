package com.earnings.dashboard.dto;

import java.math.BigDecimal;

public class EarningsSummary {
    private BigDecimal yearToDate;
    private BigDecimal monthly;
    private BigDecimal weekly;
    private String period;
    
    // Constructors
    public EarningsSummary() {}
    
    public EarningsSummary(BigDecimal yearToDate, BigDecimal monthly, BigDecimal weekly, String period) {
        this.yearToDate = yearToDate;
        this.monthly = monthly;
        this.weekly = weekly;
        this.period = period;
    }
    
    // Getters and Setters
    public BigDecimal getYearToDate() { return yearToDate; }
    public void setYearToDate(BigDecimal yearToDate) { this.yearToDate = yearToDate; }
    
    public BigDecimal getMonthly() { return monthly; }
    public void setMonthly(BigDecimal monthly) { this.monthly = monthly; }
    
    public BigDecimal getWeekly() { return weekly; }
    public void setWeekly(BigDecimal weekly) { this.weekly = weekly; }
    
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
