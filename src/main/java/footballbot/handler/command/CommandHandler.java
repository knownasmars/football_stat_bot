package footballbot.handler.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {

    boolean canHandle(String messageText);

    void handle(Update update);
}