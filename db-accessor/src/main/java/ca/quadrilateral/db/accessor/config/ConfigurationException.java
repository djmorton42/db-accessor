package ca.quadrilateral.db.accessor.config;

public class ConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(final String message) {
		super(message);
	}

	public ConfigurationException(final Throwable cause) {
		super(cause);
	}	
}
