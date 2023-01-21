package com.Loan.delegate;

import com.Loan.dao.Loans;
import com.Loan.repo.LoansRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetLoanDetails implements JavaDelegate {
    @Autowired
    public LoansRepository loansRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Loans loan = loansRepository.findById(Long.valueOf((String) delegateExecution.getVariable("loanRef"))).orElse(null);
        if (loan == null) {
            delegateExecution.setVariable("stat", false);
        } else if (loan.getIsActive() == true) {
            delegateExecution.setVariable("stat", true);
            delegateExecution.setVariable("loanBal", loan.getLoanBal());
        } else {
            delegateExecution.setVariable("stat", false);
        }
        ObjectValue typedUserValue = Variables.objectValue(loan).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("loan", typedUserValue);
        System.out.println(loan);
    }
}
