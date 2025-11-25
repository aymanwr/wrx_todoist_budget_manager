package com.tbm.task_budget_manager.budget.dto;

import com.tbm.task_budget_manager.budget.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTxRequest {
    private BigDecimal amount;
    private TransactionType type; // INCOME/EXPENSE
    private String name;          // اختياري
    private String category;      // اختياري
    private String note;          // اختياري
    private LocalDate date;       // yyyy-MM-dd
    private String receiptUrl;    // اختياري
    private String receiptPublicId; // اختياري

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getReceiptUrl() { return receiptUrl; }
    public void setReceiptUrl(String receiptUrl) { this.receiptUrl = receiptUrl; }

    public String getReceiptPublicId() { return receiptPublicId; }
    public void setReceiptPublicId(String receiptPublicId) { this.receiptPublicId = receiptPublicId; }
}
