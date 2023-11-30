package ua.com.alevel.exceptions;

public class AuthorizationException extends RuntimeException {

    private final String message;

    public AuthorizationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
