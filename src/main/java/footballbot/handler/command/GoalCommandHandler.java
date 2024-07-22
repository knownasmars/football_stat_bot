package footballbot.handler.command;

import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GoalCommandHandler implements CommandHandler {

    private final PlayerService playerService;

    private final MessageService messageService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.startsWith("/goal ");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getText().substring(6);
        playerService.recordGoal(username);
        messageService.sendResponse(chatId, username + " scored a goal!");
    }
}
