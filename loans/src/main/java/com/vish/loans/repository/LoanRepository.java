package com.vish.loans.repository;

import com.vish.loans.model.LoanInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<LoanInfo, Long> {
    List<LoanInfo> findByCustomerIdOrderByStartDtDesc(int customerId);
}
