package footballbot.service;

import footballbot.model.Player;
import footballbot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean registerPlayer(String telegramName) {
        if (playerRepository.findByTelegramName(telegramName).isPresent()) {
            return false;
        }
        Player player = new Player();
        player.setTelegramName(telegramName);
        playerRepository.save(player);
        return true;
    }

    public void recordGoal(String telegramName) {
        Player player = playerRepository.findByTelegramName(telegramName).orElseThrow(() -> new RuntimeException("Player not found"));
        player.setGoals(player.getGoals() + 1);
        playerRepository.save(player);
    }

    public void recordAssist(String telegramName) {
        Player player = playerRepository.findByTelegramName(telegramName).orElseThrow(() -> new RuntimeException("Player not found"));
        player.setAssists(player.getAssists() + 1);
        playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}