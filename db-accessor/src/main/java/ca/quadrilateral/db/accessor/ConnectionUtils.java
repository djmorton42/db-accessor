package ca.quadrilateral.db.accessor;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtils.class);
	public enum CompleteAction {
		COMMIT,
		COMMIT_ROLLBACK_ON_ERROR,
		ROLLBACK
	}
	
    public static void closeResources(final AutoCloseable... resources) {
        for (final AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (final Exception e) {
                    LOGGER.warn("Error closing DB Resource", e);
                }
            }
        }
    }

    public static void completeTransaction(final Connection connection, final CompleteAction completeAction) {
        final boolean commit = 
                completeAction == CompleteAction.COMMIT || completeAction == CompleteAction.COMMIT_ROLLBACK_ON_ERROR;
    	try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (final SQLException e) {
            if (commit && completeAction == CompleteAction.COMMIT_ROLLBACK_ON_ERROR) {
                try {
                    connection.rollback();
                } catch (final SQLException rollbackException) {
                    LOGGER.warn("Error rolling back transaction", rollbackException);
                }
            }
            throw new DatabaseAccessorException(
                    "Error completing transaction with " + (commit ? "COMMIT" : "ROLLBACK"), e
            );
        }
    }

    public static void closeConnection(final Connection connection) {
        try {
            connection.close();
        } catch (final SQLException e) {
            LOGGER.warn("Error closing connection", e);
        }
    }
}
