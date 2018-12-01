package ca.quadrilateral.db.accessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ca.quadrilateral.db.accessor.config.Configuration;
import ca.quadrilateral.db.accessor.config.IConfiguration;
import ca.quadrilateral.db.accessor.provider.IConnectionProvider;

public class DBAccessor {
    private static final ConcurrentMap<IConfiguration, IConnectionProvider> connectionProviderMap = 
            new ConcurrentHashMap<>();
    
    public static IConnectionProvider getConnectionProvider() {
        return getConnectionProvider(Configuration.loadConfiguration());
    }
    
    public static IConnectionProvider getConnectionProvider(final IConfiguration configuration) {
        return connectionProviderMap.computeIfAbsent(configuration, (k) -> k.connectionProvider());
    }
}
