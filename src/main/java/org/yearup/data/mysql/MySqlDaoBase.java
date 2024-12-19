package org.yearup.data.mysql;

import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class MySqlDaoBase
{
    private DataSource dataSource;

    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public abstract ShoppingCart addProductToCart(int userId, int productId);

    public abstract ShoppingCart updateCart(int userId, int productId, int quantity);

    public abstract ShoppingCart clearCart(int userId);
}
