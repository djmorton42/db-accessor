package ca.quadrilateral.db.accessor;

public class DatabaseAccessorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DatabaseAccessorException() {
        super();
    }

    public DatabaseAccessorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DatabaseAccessorException(final String message) {
        super(message);
    }

    public DatabaseAccessorException(final Throwable cause) {
        super(cause);
    }
}
