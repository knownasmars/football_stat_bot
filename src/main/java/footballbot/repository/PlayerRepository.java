package footballbot.repository;

import footballbot.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends  JpaRepository<Player, Long> {
    Optional<Player> findByTelegramName(String telegram);
}
