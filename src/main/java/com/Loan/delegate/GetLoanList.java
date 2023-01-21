package com.Loan.delegate;

import com.Loan.dao.Loans;
import com.Loan.repo.LoansRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetLoanList implements JavaDelegate {
    @Autowired
    public LoansRepository loansRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        List<Loans> loanList = loansRepository.findAll();
        ObjectValue typedUserValue = Variables.objectValue(loanList).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("loanList", typedUserValue);
        System.out.println(loanList);
    }
}
