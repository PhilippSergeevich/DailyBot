package org.example.service;

import org.example.confif.BotConfig;
import org.example.parser.CourseParser;
import org.example.parser.NewsParser;
import org.example.parser.WeatherParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            System.out.println(chatId);
            System.out.println(messageText);
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Sorry not now", "HTML");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        CourseParser courseParser = new CourseParser();
        WeatherParser weatherParser = new WeatherParser();
        NewsParser newsParser = new NewsParser();
        String news = newsParser.runParse();
        String weather = weatherParser.runParse();
        String parsingStr = courseParser.runParse();

        String answer = "<b>Hi, Nerds! </b>" + "\n" + "<b>GL&HF today ♥  </b>" + "\n" + "\n" + "<b>Курс валют :</b>"+"\n"  +  parsingStr + "\n" + "<b>Прогноз погоды в Мск:  </b>" + weather
                + "\n" + "\n" + "<b>Главное к этому часу :</b> " + "<i>" + news + "</i>" + "\n" + "\n"  + "<i>*Информация в сообщении предоставлена при помощи ЦБ И РБК</i>";
        sendMessage(chatId, answer, "HTML");
        log.info("Replied to user " + name);
    }

    private void sendMessage(long chatId, String textToSend, String parseMode) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setParseMode(parseMode);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occrred: " + e.getMessage());
        }
    }
}
