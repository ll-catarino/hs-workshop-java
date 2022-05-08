package io.ytech.hsworkshopjava.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "despesas")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping(path = "{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    public void addExpense(@RequestBody Expense expense) {
        expenseService.addExpense(expense);
    }

    @PutMapping(path = "{id}")
    public void updateExpense(
            @PathVariable("id") Long id,
            @RequestBody Expense expense
    ) {
        expenseService.updateExpense(id, expense);
    }

    @DeleteMapping(path = "{id}")
    public void deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
    }
}
