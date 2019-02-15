package ca.quadrilateral.db.accessor.hooks;

public interface IPostExecuteHook {
    void afterExecution(final String methodName, final String sql, final long executionDuration);
}
