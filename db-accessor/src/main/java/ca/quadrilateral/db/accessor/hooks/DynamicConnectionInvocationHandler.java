package ca.quadrilateral.db.accessor.hooks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicConnectionInvocationHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicConnectionInvocationHandler.class);

    private final Object target;

    public DynamicConnectionInvocationHandler(final Connection connection) {
        this.target = connection;
    }

    @Override
    public Object invoke(
            final Object proxy,
            final Method method,
            final Object[] args
    ) throws Throwable {
        if ("prepareStatement".equals(method.getName())) {
            final PreparedStatement statement = (PreparedStatement)method.invoke(target, args);
            return Proxy.newProxyInstance(
                    DynamicConnectionInvocationHandler.class.getClassLoader(),
                    new Class[] { PreparedStatement.class },
                    new PreparedStatementInvocationHandler(statement, (String)args[0])
            );
        } else if ("createStatement".equals(method.getName())) {
            final Statement statement = (Statement)method.invoke(target, args);
            return Proxy.newProxyInstance(
                    DynamicConnectionInvocationHandler.class.getClassLoader(),
                    new Class[] { Statement.class },
                    new StatementInvocationHandler(statement)
            );
        }

        return method.invoke(target, args);
    }

}
