package ca.quadrilateral.db.accessor.hooks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecutionHandler {
    public Object handle(final Object target, final Method method, final Object[] args, final String sql) throws Throwable {
        final long startTime = System.nanoTime();

        try {
            final Object result = method.invoke(target, args);
            return result;
        } catch (final InvocationTargetException e) {
            throw e.getCause();
        } finally {
            final long endTime = System.nanoTime();
            long durationMillis = (endTime - startTime) / 100_000;
            DbAccessorHookRegistry
                .getPostExecuteHooks()
                .stream()
                .forEach(hook -> hook.afterExecution(method.getName(), sql, durationMillis));
        }
    }
}
