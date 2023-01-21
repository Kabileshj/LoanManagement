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
public class RepayLoan implements JavaDelegate {
    @Autowired
    public LoansRepository loansRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Loans loan = loansRepository.findById(Long.valueOf((String) delegateExecution.getVariable("loanRef"))).orElse(null);
        if (delegateExecution.getVariable("rpyAmt") != null) {
            loan.setLoanBal(loan.getLoanBal() - Double.parseDouble((String) delegateExecution.getVariable("rpyAmt")));
        }
        if (loan.getLoanBal() <= 0) {
            loan.setLoanBal(0.0);
            loan.setIsActive(false);
            loan.setClosedOn(new Date());
        }
        Loans loanOut = loansRepository.save(loan);
        ObjectValue typedUserValue = Variables.objectValue(loanOut).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("loan", typedUserValue);
        System.out.println(loanOut);
    }
}