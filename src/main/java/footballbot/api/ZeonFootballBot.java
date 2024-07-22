package footballbot.api;

import footballbot.config.BotProperties;
import footballbot.handler.command.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class ZeonFootballBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(ZeonFootballBot.class);

    private final BotProperties botProperties;

    private final List<CommandHandler> handlers;

    @Autowired
    public ZeonFootballBot(BotProperties botProperties, List<CommandHandler> handlers) {
        this.botProperties = botProperties;
        this.handlers = handlers;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            for (CommandHandler handler : handlers) {
                if (handler.canHandle(messageText)) {
                    handler.handle(update);
                    break;
                }
            }
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