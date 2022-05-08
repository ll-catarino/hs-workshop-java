package io.ytech.hsworkshopjava.income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "receitas")
public class IncomeController {
    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping(path = "{id}")
    public Income getIncomeById(@PathVariable("id") Long id) {
        return incomeService.getIncomeById(id);
    }

    @PostMapping
    public void addIncome(@RequestBody Income income) {
        incomeService.addIncome(income);
    }

    @PutMapping(path = "{id}")
    public void updateIncome(
            @PathVariable("id") Long id,
            @RequestBody Income income
    ) {
        incomeService.updateIncome(id, income);
    }

    @DeleteMapping(path = "{id}")
    public void deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteIncome(id);
    }
}
