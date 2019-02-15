package ca.quadrilateral.db.accessor;

import java.sql.Connection;
import java.util.function.Function;

import ca.quadrilateral.db.accessor.ConnectionUtils.CompleteAction;
import ca.quadrilateral.db.accessor.provider.IConnectionProvider;

public class Transaction {
    public static enum TransactionMode {
        READ_WRITE,
        READ_ONLY
    }

    public static class TransactionResult<T> {
        public final boolean isSuccessful;
        public final T result;

        public TransactionResult(final boolean successful, final T result) {
            this.isSuccessful = successful;
            this.result = result;
        }

        public static <T> TransactionResult<T> success(final T result) {
            return new TransactionResult<T>(true, result);
        }

        public static <T> TransactionResult<T> failure(final T result) {
            return new TransactionResult<T>(false, result);
        }
    }

    public static <T> T doInTransaction(
            final IConnectionProvider connectionProvider,
            final Function<Connection, TransactionResult<T>> workToDo
    ) {
        return doInTransaction(
                connectionProvider,
                TransactionMode.READ_WRITE,
                workToDo
        );
    }

    public static <T> T doInTransaction(
            final Function<Connection, TransactionResult<T>> workToDo
    ) {
        return doInTransaction(
                DBAccessor.getConnectionProvider(),
                TransactionMode.READ_WRITE,
                workToDo
        );
    }

    public static <T> T doInReadOnlyTransaction(
            final IConnectionProvider connectionProvider,
            final Function<Connection, TransactionResult<T>> workToDo
    ) {
        return doInTransaction(
                connectionProvider,
                TransactionMode.READ_ONLY,
                workToDo
        );
    }

    public static <T> T doInReadOnlyTransaction(
            final Function<Connection, TransactionResult<T>> workToDo
    ) {
        return doInTransaction(
                DBAccessor.getConnectionProvider(),
                TransactionMode.READ_ONLY,
                workToDo
        );
    }

    public static <T> T doInTransaction(
            final IConnectionProvider connectionProvider,
            final TransactionMode transactionMode,
            final Function<Connection, TransactionResult<T>> workToDo) {

        final Connection connection = transactionMode == TransactionMode.READ_ONLY
                ? connectionProvider.getReadOnlyConnection()
                : connectionProvider.getConnection();

        boolean successful = false;
        try {
            final TransactionResult<T> transactionResult = workToDo.apply(connection);
            successful = transactionResult.isSuccessful;
            return transactionResult.result;
        } catch (final Exception e) {
            successful = false;
            throw e;
        } finally {
            ConnectionUtils.completeTransaction(
                    connection,
                    successful ? CompleteAction.COMMIT : CompleteAction.ROLLBACK
            );
            ConnectionUtils.closeConnection(connection);
        }
    }
}
