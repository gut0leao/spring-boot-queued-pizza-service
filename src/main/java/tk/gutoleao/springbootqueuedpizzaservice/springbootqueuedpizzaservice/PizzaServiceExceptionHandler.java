package tk.gutoleao.springbootqueuedpizzaservice.springbootqueuedpizzaservice;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.to.ApiErrorTo;

@RestControllerAdvice
@Slf4j
public class PizzaServiceExceptionHandler {

    private static final String MESSAGE_UNEXPECTED_ERROR = "Unexpected error: ";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorTo> exception(final Exception e) {
        return getInternalError(e);
    }

    private ResponseEntity<ApiErrorTo> getInternalError(final Exception e) {
        final String errorCode = getErrorCode();
        final String errorMsg = MESSAGE_UNEXPECTED_ERROR + errorCode;
        log.error(errorMsg, e);
        ApiErrorTo apiErrorDto = new ApiErrorTo(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        return new ResponseEntity<>(apiErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getErrorCode() {
        final String hexTimestamp = Long.toHexString(System.currentTimeMillis()).toUpperCase();
        String randomHex = Integer.toHexString(ThreadLocalRandom.current().nextInt(1000, 10000)).toUpperCase();
        return hexTimestamp + "@" + randomHex;
    }

    @ExceptionHandler(value = InvalidOrderException.class)
    public ResponseEntity<ApiErrorTo> exception(final InvalidOrderException e) {
        log.warn(e.getMessage(), e);
        ApiErrorTo apiError = new ApiErrorTo(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
