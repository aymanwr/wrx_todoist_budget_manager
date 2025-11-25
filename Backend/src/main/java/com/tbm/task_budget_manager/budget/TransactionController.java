package com.tbm.task_budget_manager.budget;

import com.tbm.task_budget_manager.budget.dto.CreateTxRequest;
import com.tbm.task_budget_manager.budget.dto.SummaryResponse;
import com.tbm.task_budget_manager.budget.dto.TxResponse;
import com.tbm.task_budget_manager.budget.dto.UpdateTxRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/tx")
public class TransactionController {

    private final TransactionService service;
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TxResponse> list(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "DESC") String dir
    ) {
        return service.list(from, to, page, size, sort, dir);
    }

    @GetMapping("/summary")
    public SummaryResponse summary(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return service.summary(from, to);
    }

    @GetMapping("/{id}")
    public TxResponse one(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public TxResponse create(@RequestBody CreateTxRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public TxResponse update(@PathVariable Long id, @RequestBody UpdateTxRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
