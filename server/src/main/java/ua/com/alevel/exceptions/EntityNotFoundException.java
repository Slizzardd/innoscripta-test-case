package ua.com.alevel.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private final String message;
    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
