package ca.quadrilateral.db.accessor.provider;

import java.sql.Connection;

public interface IConnectionProvider {
    Connection getConnection();

    Connection getReadOnlyConnection();
}
