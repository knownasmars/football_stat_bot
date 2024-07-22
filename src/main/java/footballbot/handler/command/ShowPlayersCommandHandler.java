package footballbot.handler.command;

import footballbot.model.Player;
import footballbot.service.MessageService;
import footballbot.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowPlayersCommandHandler implements CommandHandler {

    private final MessageService messageService;
    private final PlayerService playerService;

    @Override
    public boolean canHandle(String messageText) {
        return messageText.equalsIgnoreCase("Show Players");
    }

    @Override
    public void handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Player> players = playerService.getAllPlayers();
        String responseText = messageService.formatPlayersList(players);
        messageService.sendResponse(chatId, responseText);
    }
}