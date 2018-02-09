package by.epam.tunnel.exceptions;

public class DataLoadException extends Exception {

    public DataLoadException() {
    }

    public DataLoadException(String message) {
        super(message);
    }

    public DataLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataLoadException(Throwable cause) {
        super(cause);
    }
}
