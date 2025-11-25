package com.tbm.task_budget_manager.budget.dto;

import java.math.BigDecimal;

public class SummaryResponse {
    private BigDecimal income;
    private BigDecimal expenses;

    public SummaryResponse(BigDecimal income, BigDecimal expenses) {
        this.income = income;
        this.expenses = expenses;
    }
    public BigDecimal getIncome() { return income; }
    public BigDecimal getExpenses() { return expenses; }
}
