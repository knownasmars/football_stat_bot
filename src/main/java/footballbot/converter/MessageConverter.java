package footballbot.converter;

import footballbot.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageConverter {

    public String formatPlayersList(List<Player> players) {
        StringBuilder responseText = new StringBuilder("Players:\n");
        for (Player player : players) {
            responseText.append(player.getFirstName()).append(" (").append(player.getTelegramName()).append(")\n");
        }
        return responseText.toString();
    }
}