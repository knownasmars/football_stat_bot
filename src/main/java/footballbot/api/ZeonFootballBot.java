package footballbot.api;

import footballbot.config.BotProperties;
import footballbot.handler.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZeonFootballBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;

    private final List<CommandHandler> handlers;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update);

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