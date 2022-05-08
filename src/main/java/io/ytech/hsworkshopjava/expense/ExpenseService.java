package io.ytech.hsworkshopjava.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void addExpense(Expense expense) {
        descriptionAndDateValidOrElseThrow(expense);

        expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);

        if (expenseOptional.isEmpty()) {
            throw new IllegalStateException("Error: no expense with id " + id);
        }

        return expenseOptional.get();
    }

    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Error: no expense with id " + id);
        }
    }

    @Transactional
    public void updateExpense(Long id, Expense newExpense) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);

        if (expenseOptional.isEmpty()) {
            throw new IllegalStateException("Error: no expense with id " + id);
        }

        Expense expense = expenseOptional.get();

        if (newExpense.getDate() == null) {
            newExpense.setDate(expense.getDate());
        }
        if (newExpense.getDescription() == null || newExpense.getDescription().length() == 0) {
            newExpense.setDescription(expense.getDescription());
        }
        descriptionAndDateValidOrElseThrow(newExpense);

        expense.setDescription(newExpense.getDescription());
        expense.setDate(newExpense.getDate());

        if (newExpense.getAmount() != null && newExpense.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            expense.setAmount(newExpense.getAmount());
        }
    }

    private void descriptionAndDateValidOrElseThrow(Expense expense) {
        List<Expense> expensesWithSameDescription = expenseRepository.findAllByDescription(expense.getDescription());

        Iterator<Expense> it = expensesWithSameDescription.iterator();

        while (it.hasNext()) {
            LocalDate otherExpenseDate = it.next().getDate();
            LocalDate newExpenseDate = expense.getDate();

            if (otherExpenseDate.getYear() == newExpenseDate.getYear() &&
                    otherExpenseDate.getMonth().equals(newExpenseDate.getMonth())) {
                throw new IllegalStateException("Error: Expenses can't have duplicate descriptions in the same month.");
            }
        }
    }
}
