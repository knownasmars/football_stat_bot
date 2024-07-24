package footballbot.service;

import footballbot.converter.MessageConverter;
import footballbot.event.SendMessageEvent;
import footballbot.event.SendPollEvent;
import footballbot.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ApplicationEventPublisher eventPublisher;
    private final MessageConverter messageConverter;

    public void sendResponse(String chatId, String text) {
        SendMessageEvent event = new SendMessageEvent(this, chatId, text);
        eventPublisher.publishEvent(event);
    }

    public void sendResponseWithKeyboard(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = createReplyKeyboard();
        message.setReplyMarkup(keyboardMarkup);

        SendMessageEvent event = new SendMessageEvent(this, message);
        eventPublisher.publishEvent(event);
    }

    public void sendPoll(String chatId, String question, List<String> options) {
        SendPoll poll = new SendPoll();
        poll.setChatId(chatId);
        poll.setQuestion(question);
        poll.setOptions(options);
        poll.setIsAnonymous(false);

        SendPollEvent event = new SendPollEvent(this, poll);
        eventPublisher.publishEvent(event);
    }

    private ReplyKeyboardMarkup createReplyKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Register"));
        row.add(new KeyboardButton("Show Players"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public String formatPlayersList(List<Player> players) {
        return messageConverter.formatPlayersList(players);
    }
}