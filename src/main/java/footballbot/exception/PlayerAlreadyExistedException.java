package footballbot.exception;

public class PlayerAlreadyExistedException extends CustomException {

    private static final String ALREADY_EXISTED_EXCEPTION =
            "The player with given telegram already existed";

    public PlayerAlreadyExistedException() {
        super(ALREADY_EXISTED_EXCEPTION);
    }
}
