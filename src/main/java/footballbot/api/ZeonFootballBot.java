package footballbot.api;

import footballbot.config.BotPropertiesConfig;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZeonFootballBot extends TelegramLongPollingBot {

    private final PlayerService playerService;

    private final BotPropertiesConfig botProperties;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                sendResponse(chatId, "Welcome to Football Bot! Use /register to register for the next match.");
            } else if (messageText.equals("/register")) {
                playerService.registerPlayer(update.getMessage().getFrom().getUserName());
                sendResponse(chatId, "You are registered!");
            } else if (messageText.startsWith("/goal ")) {
                String username = messageText.substring(6);
                playerService.recordGoal(username);
                sendResponse(chatId, username + " scored a goal!");
            } else if (messageText.startsWith("/assist ")) {
                String username = messageText.substring(8);
                playerService.recordAssist(username);
                sendResponse(chatId, username + " made an assist!");
            }
        }
    }

    private void sendResponse(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}