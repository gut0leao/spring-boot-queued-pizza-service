package tk.gutoleao.springbootqueuedpizzaservice.exception;

public class InvalidOrderException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidOrderException() {
        super();
    }

    public InvalidOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrderException(String message) {
        super(message);
    }

    public InvalidOrderException(Throwable cause) {
        super(cause);
    }

}
