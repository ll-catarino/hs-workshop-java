package io.ytech.hsworkshopjava.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public void addIncome(Income income) {
        descriptionAndDateValidOrElseThrow(income);

        incomeRepository.save(income);
    }

    public Income getIncomeById(Long id) {
        Optional<Income> incomeOptional = incomeRepository.findById(id);

        if (incomeOptional.isEmpty()) {
            throw new IllegalStateException("Error: no income with id " + id);
        }

        return incomeOptional.get();
    }

    public void deleteIncome(Long id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Error: no income with id " + id);
        }
    }

    @Transactional
    public void updateIncome(Long id, Income newIncome) {
        Optional<Income> incomeOptional = incomeRepository.findById(id);

        if (incomeOptional.isEmpty()) {
            throw new IllegalStateException("Error: no income with id " + id);
        }

        Income income = incomeOptional.get();

        if (newIncome.getDate() == null) {
            newIncome.setDate(income.getDate());
        }
        if (newIncome.getDescription() == null || newIncome.getDescription().length() == 0) {
            newIncome.setDescription(income.getDescription());
        }
        descriptionAndDateValidOrElseThrow(newIncome);

        income.setDescription(newIncome.getDescription());
        income.setDate(newIncome.getDate());

        if (newIncome.getAmount() != null && newIncome.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            income.setAmount(newIncome.getAmount());
        }
    }

    private void descriptionAndDateValidOrElseThrow(Income income) {
        List<Income> incomesWithSameDescription = incomeRepository.findAllByDescription(income.getDescription());

        Iterator<Income> it = incomesWithSameDescription.iterator();

        while (it.hasNext()) {
            LocalDate otherIncomeDate = it.next().getDate();
            LocalDate newIncomeDate = income.getDate();

            if (otherIncomeDate.getYear() == newIncomeDate.getYear() &&
                    otherIncomeDate.getMonth().equals(newIncomeDate.getMonth())) {
                throw new IllegalStateException("Error: Incomes can't have duplicate descriptions in the same month.");
            }
        }
    }
}
