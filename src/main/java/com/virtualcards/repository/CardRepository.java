package com.virtualcards.repository;

import com.virtualcards.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    public List<Card> findByUserId(Long userId);

}