package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = """
                SELECT *
                FROM shopping_cart
                WHERE user_id = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            ShoppingCart shoppingCart = new ShoppingCart();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    int quantity = resultSet.getInt("quantity");
                    int product_id = resultSet.getInt("product_id");

                    // get product from db via id
                    Product productFromDB = productDao.getById(product_id);
                    // create shoppingCartItem that has this product
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setProduct(productFromDB);
                    shoppingCartItem.setQuantity(quantity);

                    // add to hashmap (productId : shoppingCarItem)
                    shoppingCart.add(shoppingCartItem);
                    System.err.println(shoppingCart);
                }
                // return a populated shopping cart
                return shoppingCart;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShoppingCart create(int userId, int productId) {
        ShoppingCart shoppingCart = getByUserId(userId);

        ShoppingCartItem shoppingCartItem = shoppingCart.get(productId);

        String sql = "";
        if(shoppingCartItem == null) {
            sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) " +
                    " VALUES (?, ?, 1);";
        } else {
            sql = "UPDATE shopping_cart SET quantity = ? WHERE product_id = ? and user_id = ?";
        }

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            if(shoppingCartItem == null) {
                statement.setInt(1, userId);
                statement.setInt(2, productId);
            } else {
                statement.setInt(1, shoppingCartItem.getQuantity() + 1);
                statement.setInt(2, productId);
                statement.setInt(3, userId);
            }

            int rowsAffected = statement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getByUserId(userId);
    }


    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        String sql = """
                INSERT INTO shopping_cart
                (user_id, product_id, quantity)
                VALUES
                (?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, 1);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return this.getByUserId(userId);
    }

    @Override
    public ShoppingCart updateCart(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public ShoppingCart clearCart(int userId) {
        return null;
    }
}