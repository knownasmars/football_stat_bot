package footballbot.handler.command;

import footballbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private final MessageService messageService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.equals("/start");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        messageService.sendResponse(chatId,
                "Welcome to New Zeon Football! Use /register to register for the next match.");
    }
}