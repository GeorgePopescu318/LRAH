package Exceptions;

public class CSVException extends BaseException {
    public CSVException(String message) {
        super(message);
    }

    public CSVException(String message, Throwable cause) {
        super(message, cause);
    }
}
