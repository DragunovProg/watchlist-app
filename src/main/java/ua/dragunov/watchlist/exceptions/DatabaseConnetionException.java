package ua.dragunov.watchlist.exceptions;

public class DatabaseConnetionException extends RuntimeException {

    public DatabaseConnetionException() {
        super();
    }

    public DatabaseConnetionException(String message) {
        super(message);
    }

    public DatabaseConnetionException(String message, Throwable cause) {
        super(message, cause);
    }


}
