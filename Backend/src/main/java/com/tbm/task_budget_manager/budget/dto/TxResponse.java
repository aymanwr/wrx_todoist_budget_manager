package com.tbm.task_budget_manager.budget.dto;

import com.tbm.task_budget_manager.budget.Transaction;
import com.tbm.task_budget_manager.budget.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TxResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String name;
    private String category;
    private String note;
    private LocalDate date;
    private String receiptUrl;
    private String receiptPublicId;

    public static TxResponse from(Transaction t) {
        TxResponse r = new TxResponse();
        r.id = t.getId();
        r.amount = t.getAmount();
        r.type = t.getType();
        r.name = t.getName();
        r.category = t.getCategory();
        r.note = t.getNote();
        r.date = t.getDate();
        r.receiptUrl = t.getReceiptUrl();
        r.receiptPublicId = t.getReceiptPublicId();
        return r;
    }

    // Getters
    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getNote() { return note; }
    public LocalDate getDate() { return date; }
    public String getReceiptUrl() { return receiptUrl; }
    public String getReceiptPublicId() { return receiptPublicId; }
}
