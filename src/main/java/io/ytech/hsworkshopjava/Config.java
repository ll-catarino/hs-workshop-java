package io.ytech.hsworkshopjava;

import io.ytech.hsworkshopjava.expense.Expense;
import io.ytech.hsworkshopjava.expense.ExpenseRepository;
import io.ytech.hsworkshopjava.income.Income;
import io.ytech.hsworkshopjava.income.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class Config {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    IncomeRepository incomeRepository;

    @Bean
    public List<Expense> listExpenses() {
        Expense d1 = new Expense("Telemóvel", BigDecimal.valueOf(21.35), LocalDate.now());
        Expense d2 = new Expense("Energia", BigDecimal.valueOf(40.5), LocalDate.now());
        Expense d3 = new Expense("Água", BigDecimal.valueOf(30.30), LocalDate.now());

        List<Expense> list = List.of(d1, d2, d3);

        return expenseRepository.saveAll(list);
    }

    @Bean
    public List<Income> listIncome() {
        Income r1 = new Income("ordenado", BigDecimal.valueOf(1000.0), LocalDate.now());
        Income r2 = new Income("euromilhões", BigDecimal.valueOf(15.6), LocalDate.now());
        Income r3 = new Income("ações da ytech", BigDecimal.valueOf(.40), LocalDate.now());
        List<Income> list = List.of(r1, r2, r3);

        return incomeRepository.saveAll(list);
    }

}
