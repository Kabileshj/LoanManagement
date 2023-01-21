package com.Loan.delegate;

import com.Loan.dao.Loans;
import com.Loan.repo.LoansRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreateLoan implements JavaDelegate {
    @Autowired
    public LoansRepository loansRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Loans loan = new Loans();
        if (delegateExecution.getVariable("loanBal") != null) {
            loan.setLoanBal(Double.parseDouble((String) delegateExecution.getVariable("loanBal")));
        }
        if (delegateExecution.getVariable("loanTenure") != null) {
            loan.setLoanTenure((Integer) delegateExecution.getVariable("loanTenure"));
        }
        loan.setCreatedOn(new Date());
        loan.setIsActive(true);
        loan.setClosedOn(null);
        Loans loanOut = loansRepository.save(loan);
        ObjectValue typedUserValue = Variables.objectValue(loanOut).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("loan", typedUserValue);
        System.out.println(loanOut);
    }
}