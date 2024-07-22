package footballbot.api;

import footballbot.config.BotProperties;
import footballbot.handler.command.CommandHandler;
import footballbot.model.Player;
import footballbot.service.ChatIdService;
import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ZeonFootballBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(ZeonFootballBot.class);

    private final BotProperties botProperties;
    private final List<CommandHandler> handlers;
    private final MessageService messageService;
    private final PlayerService playerService;
    private final ChatIdService chatIdService;

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            chatIdService.addChatId(chatId);

            if (messageText.equals("/start")) {
                messageService.sendResponseWithKeyboard(chatId, "Welcome! Please choose an option:");
            } else {
                for (CommandHandler handler : handlers) {
                    if (handler.canHandle(messageText)) {
                        handler.handle(update);
                        break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            handleCallbackQuery(update.getCallbackQuery().getMessage().getChatId().toString(), callbackData);
        }
    }

    private void handleCallbackQuery(String chatId, String callbackData) {
        if (callbackData.startsWith("play_")) {
            String matchDate = callbackData.substring(5);
            messageService.sendResponse(chatId, "Вы зарегистрированы на матч " + matchDate);
        } else if (callbackData.startsWith("skip_")) {
            String matchDate = callbackData.substring(5);
            messageService.sendResponse(chatId, "Вы пропускаете матч " + matchDate);
        } else if (callbackData.equals("show_players")) {
            List<Player> players = playerService.getAllPlayers();
            String responseText = messageService.formatPlayersList(players);
            messageService.sendResponse(chatId, responseText);
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