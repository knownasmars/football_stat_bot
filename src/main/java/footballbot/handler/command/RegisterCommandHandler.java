package footballbot.handler.command;

import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegisterCommandHandler implements CommandHandler {

    private final PlayerService playerService;

    private final MessageService messageService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.equals("/register");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getFrom().getUserName();
        playerService.registerPlayer(username);
        messageService.sendResponse(chatId, "You are registered!");
    }
}