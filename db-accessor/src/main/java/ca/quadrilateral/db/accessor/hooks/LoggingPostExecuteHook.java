package ca.quadrilateral.db.accessor.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingPostExecuteHook implements IPostExecuteHook {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingPostExecuteHook.class);

    @Override
    public void afterExecution(final String methodName, final String sql, final long executionDuration) {
        LOGGER.info("Statement Executed from method '{}' in {} ms\n\n>>> {}\n", methodName, executionDuration, sql);
    }
}
