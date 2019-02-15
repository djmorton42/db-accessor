package ca.quadrilateral.db.accessor.hooks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatementInvocationHandler implements InvocationHandler {
    private final Statement statement;

    public StatementInvocationHandler(final Statement statement) {
        this.statement = statement;
    }

    @Override
    public Object invoke(
            final Object proxy,
            final Method method,
            final Object[] args) throws Throwable {

        if (method.getName().startsWith("execute") && !method.getName().equals("executeBatch")) {
            return new ExecutionHandler().handle(statement, method, args, (String)args[0]);
        }

        try {
            return method.invoke(statement, args);
        } catch (final InvocationTargetException e) {
            return e.getCause();
        }
    }

}
