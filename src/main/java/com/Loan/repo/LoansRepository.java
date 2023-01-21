package com.Loan.repo;

import com.Loan.dao.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {
    Optional<Loans> findById(Long loanRef);
}
