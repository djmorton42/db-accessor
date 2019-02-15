package ca.quadrilateral.db.accessor.hooks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreparedStatementInvocationHandler implements InvocationHandler {
    private final PreparedStatement statement;
    private final String sql;

    public PreparedStatementInvocationHandler(final PreparedStatement statement, final String sql) {
        this.statement = statement;
        this.sql = sql;
    }

    @Override
    public Object invoke(
            final Object proxy,
            final Method method,
            final Object[] args
    ) throws Throwable {
        if (method.getName().startsWith("execute") && !method.getName().startsWith("executeBatch")) {
            return new ExecutionHandler().handle(statement, method, args, sql);
        }

        try {
            return method.invoke(statement, args);
        } catch (final InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
