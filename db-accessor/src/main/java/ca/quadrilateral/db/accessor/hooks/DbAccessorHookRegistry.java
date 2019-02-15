package ca.quadrilateral.db.accessor.hooks;

import java.util.ArrayList;
import java.util.List;

public class DbAccessorHookRegistry {
    private static List<IPostExecuteHook> postExecuteHooks = new ArrayList<>();

    public synchronized static void addPostExecuteHook(final IPostExecuteHook hook) {
        postExecuteHooks.add(hook);
    }

    public synchronized static List<IPostExecuteHook> getPostExecuteHooks() {
        return new ArrayList<>(postExecuteHooks);
    }
}
