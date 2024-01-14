package com.example.M2T8Lesson6.services;

import com.example.M2T8Lesson6.models.Quote;
import com.example.M2T8Lesson6.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QuoteService {
    @Autowired
    BashParser parser;
    @Autowired
    QuoteRepository repository;

    public List<Quote> getIndex() {
        List<Quote> ret = new ArrayList<>();
        try {
            Map<Integer, String> map = null;
            map = parser.getIndex();

            for (var entry : map.entrySet()) {
                var rawQuote = new Quote();
                rawQuote.setQuoteId(entry.getKey());
                rawQuote.setText(entry.getValue());
                var existed = repository.findByQuoteIdEquals(rawQuote.getQuoteId());
                if (existed.isEmpty()) {
                    ret.add(repository.save(rawQuote));
                }else ret.add(existed.get());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
