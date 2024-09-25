package ua.dragunov.watchlist.exceptions;

public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException() {
        super();
    }

    public InvalidInputDataException(String message) {
        super(message);
    }

    public InvalidInputDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
