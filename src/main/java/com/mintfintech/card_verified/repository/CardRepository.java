package com.mintfintech.card_verified.repository;

import com.mintfintech.card_verified.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCardNo(String cardNo);

    Page<Card> findAll(Pageable pageable);
}