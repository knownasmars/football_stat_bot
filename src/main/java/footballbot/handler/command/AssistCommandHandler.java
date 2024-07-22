package footballbot.handler.command;

import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AssistCommandHandler implements CommandHandler {

    private final PlayerService playerService;

    private final MessageService messageService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.startsWith("/assist ");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getText().substring(8);
        playerService.recordAssist(username);
        messageService.sendResponse(chatId, username + " made an assist!");
    }
}