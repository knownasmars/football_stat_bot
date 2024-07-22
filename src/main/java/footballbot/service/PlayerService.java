package footballbot.service;

import footballbot.exception.PlayerAlreadyExistedException;
import footballbot.exception.PlayerNotFoundException;
import footballbot.model.Player;
import footballbot.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public void registerPlayer(String telegramName) {
        if (playerRepository.findByTelegramName(telegramName).isPresent()) {
            throw new PlayerAlreadyExistedException();
        }
        Player player = new Player();
        player.setTelegramName(telegramName);
        player.setGoals(0);
        player.setAssists(0);
        playerRepository.save(player);
    }

    @Transactional
    public void recordGoal(String telegramName) {
        Player player = playerRepository.findByTelegramName(telegramName)
                .orElseThrow(PlayerNotFoundException::new);
        if (player != null) {
            player.setGoals(player.getGoals() + 1);
            playerRepository.save(player);
        }
    }

    @Transactional
    public void recordAssist(String telegramName) {
        Player player = playerRepository.findByTelegramName(telegramName)
                .orElseThrow(PlayerNotFoundException::new);
        player.setAssists(player.getAssists() + 1);
        playerRepository.save(player);
    }

    public Player getPlayer(String telegramName) {
        return playerRepository.findByTelegramName(telegramName)
                .orElseThrow(PlayerNotFoundException::new);
    }
}