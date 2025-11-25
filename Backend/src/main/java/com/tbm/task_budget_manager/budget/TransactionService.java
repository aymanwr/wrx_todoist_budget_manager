package com.tbm.task_budget_manager.budget;

import com.tbm.task_budget_manager.budget.dto.CreateTxRequest;
import com.tbm.task_budget_manager.budget.dto.SummaryResponse;
import com.tbm.task_budget_manager.budget.dto.TxResponse;
import com.tbm.task_budget_manager.budget.dto.UpdateTxRequest;
import com.tbm.task_budget_manager.user.User;
import com.tbm.task_budget_manager.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.tbm.task_budget_manager.budget.TransactionSpecifications.byUserAndDate;

@Service
public class TransactionService {

    private final TransactionRepository repo;
    private final UserService userService;

    public TransactionService(TransactionRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    public TxResponse create(CreateTxRequest req) {
        User me = userService.getCurrentUser();
        Transaction t = new Transaction();
        t.setUser(me);
        t.setAmount(req.getAmount());
        t.setType(req.getType());
        t.setName(req.getName());
        t.setCategory(req.getCategory());
        t.setNote(req.getNote());
        t.setReceiptUrl(req.getReceiptUrl());
        t.setReceiptPublicId(req.getReceiptPublicId());
        t.setDate(req.getDate() != null ? req.getDate() : LocalDate.now());
        t = repo.save(t);
        return TxResponse.from(t);
    }

    public TxResponse update(Long id, UpdateTxRequest req) {
        User me = userService.getCurrentUser();
        Transaction t = repo.findById(id).orElseThrow();
        if (!t.getUser().getId().equals(me.getId())) {
            throw new IllegalStateException("Not your transaction");
        }
        if (req.getAmount() != null) t.setAmount(req.getAmount());
        if (req.getType() != null) t.setType(req.getType());
        if (req.getName() != null) t.setName(req.getName());
        if (req.getCategory() != null) t.setCategory(req.getCategory());
        if (req.getNote() != null) t.setNote(req.getNote());
        if (req.getDate() != null) t.setDate(req.getDate());
        if (req.getReceiptUrl() != null) t.setReceiptUrl(req.getReceiptUrl());
        if (req.getReceiptPublicId() != null) t.setReceiptPublicId(req.getReceiptPublicId());
        t = repo.save(t);
        return TxResponse.from(t);
    }

    public void delete(Long id) {
        User me = userService.getCurrentUser();
        Transaction t = repo.findById(id).orElseThrow();
        if (!t.getUser().getId().equals(me.getId())) {
            throw new IllegalStateException("Not your transaction");
        }
        repo.delete(t);
    }

    public Page<TxResponse> list(LocalDate from, LocalDate to, int page, int size, String sort, String dir) {
        User me = userService.getCurrentUser();
        Sort.Direction direction = "ASC".equalsIgnoreCase(dir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pr = PageRequest.of(page, size, Sort.by(direction, sort == null ? "date" : sort));
        Specification<Transaction> spec = byUserAndDate(me, from, to);
        return repo.findAll(spec, pr).map(TxResponse::from);
    }

    public SummaryResponse summary(LocalDate from, LocalDate to) {
        User me = userService.getCurrentUser();
        BigDecimal inc = repo.sumByTypeAndDate(me, TransactionType.INCOME, from, to);
        BigDecimal exp = repo.sumByTypeAndDate(me, TransactionType.EXPENSE, from, to);
        return new SummaryResponse(inc, exp);
    }

    public TxResponse findOne(Long id) {
        User me = userService.getCurrentUser();
        Transaction t = repo.findById(id).orElseThrow();
        if (!t.getUser().getId().equals(me.getId())) throw new IllegalStateException("Not your transaction");
        return TxResponse.from(t);
    }
}
