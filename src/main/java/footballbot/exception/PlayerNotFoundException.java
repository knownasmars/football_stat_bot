package footballbot.exception;

public class PlayerNotFoundException extends CustomException {

    public static final String PLAYER_NOT_FOUND = "The player with given telegram not found";

    public PlayerNotFoundException() {
        super(PLAYER_NOT_FOUND);
    }
}
