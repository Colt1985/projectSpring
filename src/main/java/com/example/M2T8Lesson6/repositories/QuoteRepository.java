package com.example.M2T8Lesson6.repositories;

import com.example.M2T8Lesson6.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    Optional<Quote> findByQuoteIdEquals(Integer quoteId);

}
