package com.example.M2T8Lesson6.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class BashParser {
    public Map<Integer, String> getIndex() throws IOException {
        Document doc = Jsoup.connect("http://ibash.org.ru/").get();
        System.out.println(doc.title());
        Elements sourceQuotes = doc.select(".quote");
        Map<Integer, String> quotes = new HashMap<>();
        for (Element el : sourceQuotes) {
            int id = Integer.parseInt(el.select("b").first().text().substring(1));
            try {
                String text = el.select(".quotbody").first().text();
                quotes.put(id, text);
            } catch (Exception e) {
                System.out.println("Пусто");
            }
        }
        return quotes;
    }
}
