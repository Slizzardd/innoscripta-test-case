package ua.com.alevel.exceptions;

public class SaveFileException extends RuntimeException {

    private final String message;

    public SaveFileException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
