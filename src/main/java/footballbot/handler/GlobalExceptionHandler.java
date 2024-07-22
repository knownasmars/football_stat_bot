package footballbot.handler;

import footballbot.exception.CustomException;
import footballbot.handler.dto.ErrorResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
            @NonNull CustomException exception) {
        log.error(exception.getReason(), exception);
        return ResponseEntity.unprocessableEntity().body(new ErrorResponse(exception.getReason()));
    }

}
