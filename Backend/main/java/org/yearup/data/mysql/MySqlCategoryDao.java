package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        return null;
    }

    @Override
    public ShoppingCart updateCart(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public ShoppingCart clearCart(int userId) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        // get all categories
        String sql = """
                SELECT *
                FROM Categories
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                categoryList.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM categories
                    WHERE category_id = ?""")
        ) {
            statement.setInt(1, categoryId);
            ResultSet row = statement.executeQuery();

            if (row.next()) {
                return  mapRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category create(Category category) {
        // create a new category

        String sql = """
                INSERT INTO categories
                (name, description)
                VALUES
                (?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    int orderId = generatedKeys.getInt(1);

                    // get the newly inserted category
                    return getById(orderId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}