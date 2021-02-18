package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DatabaseManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryDaoJdbc.class);

    DataSource dataSource;

    private static ProductCategoryDaoJdbc instance = null;

    private ProductCategoryDaoJdbc() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dataSource = dbManager.getDataSource();
    }

    private ProductCategoryDaoJdbc(String configFileName) {
        DatabaseManager dbManager = DatabaseManager.getInstance(configFileName);
        dataSource = dbManager.getDataSource();
    }

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    public static ProductCategoryDaoJdbc getInstance(String configFileName) {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc(configFileName);
        }
        return instance;
    }

    @Override
    public void add(ProductCategory productCategory) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO product_category (name, department, description) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, productCategory.getName());
            statement.setString(2, productCategory.getDepartment());
            statement.setString(3, productCategory.getDescription());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            productCategory.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductCategory find(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT name, department,description FROM product_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new ProductCategory(
                    id,
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );

        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM product_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    private List<ProductCategory> createProductCategoryList(ResultSet resultSet)
            throws SQLException {

        List<ProductCategory> productCategories = new ArrayList<>();

        while (resultSet.next()) {
            productCategories.add(
                    new ProductCategory(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }

        return productCategories;
    }

    @Override
    public List<ProductCategory> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, department, description FROM product_category";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            logger.info("All product categories queried successfully");

            return createProductCategoryList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }
}
