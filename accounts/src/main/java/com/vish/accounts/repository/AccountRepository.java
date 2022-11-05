package com.vish.accounts.repository;

import com.vish.accounts.model.AccountInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountInfo, Long> {
    AccountInfo getByCustomerId(int customerId);
}
