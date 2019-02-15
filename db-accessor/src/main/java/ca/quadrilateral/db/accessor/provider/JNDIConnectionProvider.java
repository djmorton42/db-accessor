package ca.quadrilateral.db.accessor.provider;

import java.lang.reflect.Proxy;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ca.quadrilateral.db.accessor.DatabaseAccessorException;
import ca.quadrilateral.db.accessor.hooks.DynamicConnectionInvocationHandler;

public class JNDIConnectionProvider implements IConnectionProvider {
    private final String writeableJndiContext;
    private final String readOnlyJndiContext;

    public JNDIConnectionProvider(final String writeableJndiContext) {
        this(writeableJndiContext, null);
    }

    public JNDIConnectionProvider(final String writeableJndiContext, final String readOnlyJndiContext) {
        this.writeableJndiContext = writeableJndiContext;
        this.readOnlyJndiContext = readOnlyJndiContext;
    }

    @Override
    public Connection getConnection() {
        return getConnection(writeableJndiContext);
    }

    @Override
    public Connection getReadOnlyConnection() {
        if (readOnlyJndiContext == null) {
            throw new DatabaseAccessorException("Read Only connections not configured.");
        }
        return getConnection(readOnlyJndiContext);
    }

    private Connection getConnection(final String contextName) {
        try {
            final DataSource dataSource = (DataSource)new InitialContext().lookup(contextName);
            if (dataSource == null) {
                throw new DatabaseAccessorException("Could not retrieve Data Source via JNDI for context: " + contextName);
            }
            final Connection connection = dataSource.getConnection();

            connection.setAutoCommit(false);

            return (Connection)Proxy.newProxyInstance(
                    DynamicConnectionInvocationHandler.class.getClassLoader(),
                    new Class[] { Connection.class },
                    new DynamicConnectionInvocationHandler(connection)
            );
        } catch (final Exception e) {
            throw new DatabaseAccessorException("Error getting Data Source via JNDI for context: " + contextName, e);
        }
    }
}
