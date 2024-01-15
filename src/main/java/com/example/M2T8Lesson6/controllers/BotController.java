package com.example.M2T8Lesson6.controllers;

import com.example.M2T8Lesson6.models.Chat;
import com.example.M2T8Lesson6.models.Quote;
import com.example.M2T8Lesson6.repositories.ChatRepository;
import com.example.M2T8Lesson6.services.QuoteService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class BotController {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    QuoteService service;
    private final TelegramBot bot;
    private final String botToken = "6628218997:AAHHd9VSHZAtGi-o5G-dAbmFfsfMbg5zSOA";

    public BotController() {
        bot = new TelegramBot(botToken);

        bot.setUpdatesListener(updates -> {

            for (var update : updates) {
                handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
// Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });
    }

    private void handleUpdate(Update update) {
        String text = update.message().text();
        long id = update.message().chat().id();
        var rawChat = chatRepository.findByChatIdEquals(id);
        Chat chat ;
        if (rawChat.isPresent()){
            chat = rawChat.get();}
        else {
            var _chat = new Chat();
            _chat.setChatId(id);
            _chat.setLastId(0);
           chat = chatRepository.save(_chat);
        }
        switch (text){
            case "/start":
            case "/next":
                sendNextQuote(chat);
                break;
            case "/prev":
                sendPrevQuote(chat);
                break;
                case "/rand":
                sendRandom(chat);
                break;
        }
    }
    private void sendNextQuote(Chat chat){
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId++;
            quote = service.getById(newId);
        }
        chat.setLastId(quote.getQuoteId());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }

    private void sendPrevQuote(Chat chat){
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId--;
            if (newId < 2) newId = 2;
            quote = service.getById(newId);
        }
        chat.setLastId(quote.getQuoteId());
        chatRepository.save(chat);
        sendText(chat.getChatId(), quote.getText());
    }
    private void sendRandom(Chat chat){
        Quote quote = service.getRandom();
        sendText(chat.getChatId(), quote.getText());
    }
    private void sendText(long chatId, String text){
        bot.execute(new SendMessage(chatId,text));
    }
}
