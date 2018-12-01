package ca.quadrilateral.db.accessor.config;

import ca.quadrilateral.db.accessor.provider.IConnectionProvider;

public interface IConfiguration {
	public IConnectionProvider connectionProvider();
}
