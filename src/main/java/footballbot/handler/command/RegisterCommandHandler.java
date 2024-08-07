package footballbot.handler.command;

import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegisterCommandHandler implements CommandHandler {

    private final MessageService messageService;

    private final PlayerService playerService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.equalsIgnoreCase("Register");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getFrom().getUserName();
        boolean registered = playerService.registerPlayer(username);
        if (registered) {
            messageService.sendResponse(chatId, "You are registered!");
        } else {
            messageService.sendResponse(chatId, "You are already registered!");
        }
    }
}