package ca.quadrilateral.db.accessor.provider;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;

import ca.quadrilateral.db.accessor.DatabaseAccessorException;
import ca.quadrilateral.db.accessor.config.ConfigurationException;
import ca.quadrilateral.db.accessor.config.credentials.ICredentialsProvider;
import ca.quadrilateral.db.accessor.hooks.DynamicConnectionInvocationHandler;

public class DirectConnectionProvider implements IConnectionProvider {
    private final String writeableConnectionString;
    private final String readOnlyConnectionString;
    private final ICredentialsProvider credentialsProvider;

    public DirectConnectionProvider(
            final String driver,
            final ICredentialsProvider credentialsProvider,
            final String writeableConnectionString) {
        this(driver, credentialsProvider, writeableConnectionString, null);
    }

    public DirectConnectionProvider(
            final String driver,
            final ICredentialsProvider credentialsProvider,
            final String writeableConnectionString,
            final String readOnlyConnectionString) {

        this.credentialsProvider = credentialsProvider;
        this.writeableConnectionString = writeableConnectionString;
        this.readOnlyConnectionString = readOnlyConnectionString;

        try {
            Class.forName(driver);
        } catch (final ClassNotFoundException e) {
            throw new ConfigurationException("Driver " + driver + " could not be loaded", e);
        }
    }

    @Override
    public Connection getConnection() {
        return getConnection(writeableConnectionString);
    }

    @Override
    public Connection getReadOnlyConnection() {
        if (readOnlyConnectionString == null) {
            throw new DatabaseAccessorException("Read Only connections not configured.");
        }

        return getConnection(readOnlyConnectionString);
    }

    private Connection getConnection(final String connectionString) {
        try {
            final Connection connection = DriverManager.getConnection(
                    connectionString,
                    credentialsProvider.getUsername(),
                    credentialsProvider.getPassword()
            );
            connection.setAutoCommit(false);

            return (Connection)Proxy.newProxyInstance(
                    DynamicConnectionInvocationHandler.class.getClassLoader(),
                    new Class[] { Connection.class },
                    new DynamicConnectionInvocationHandler(connection)
            );
        } catch (final Exception e) {
            throw new DatabaseAccessorException("Error getting direct connection", e);
        }
    }
}
