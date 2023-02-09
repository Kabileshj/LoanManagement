package com.Loan.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResultWithVariables;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/loanmanagement/start")
    public ResponseEntity<String> triggerProcess(String msg, Integer cibilScore, Double acntBal) {
        MessageCorrelationResultWithVariables result = runtimeService
                .createMessageCorrelation(msg)
                .setVariable("cibilScore", cibilScore)
                .setVariable("acntBal", acntBal)
                .correlateWithResultAndVariables(true);
//        VariableMap processVariables = result.getVariables();
        ProcessInstance startedProcessInstance = result.getProcessInstance();
        return new ResponseEntity<>("processInstance id : " + startedProcessInstance.getProcessInstanceId(), HttpStatus.OK);
    }

    @PostMapping(value = "/loanmanagement/claimtask/{id}")
    public ResponseEntity<Void> claimTask(@PathVariable("id") String id, String userId) {
        taskService.claim(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/loanmanagement/completetask/{id}")
    public ResponseEntity<String> completeTask(@PathVariable("id") String id, Integer action) {
        taskService.setVariable(id, "action", action);
        taskService.complete(id);
        return new ResponseEntity<>("Chosen Action is " + action, HttpStatus.OK);
    }
}
