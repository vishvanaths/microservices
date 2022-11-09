package com.vish.cards.repository;

import com.vish.cards.model.CardInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<CardInfo, Long> {
    List<CardInfo> findByCustomerId(int customerId);
}
