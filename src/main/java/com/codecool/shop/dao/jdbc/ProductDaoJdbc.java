package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DatabaseManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProductDaoJdbc implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);

    DataSource dataSource;

    private static ProductDaoJdbc instance = null;

    private ProductDaoJdbc() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dataSource = dbManager.getDataSource();
    }

    private ProductDaoJdbc(String configFileName) {
        DatabaseManager dbManager = DatabaseManager.getInstance(configFileName);
        dataSource = dbManager.getDataSource();
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    public static ProductDaoJdbc getInstance(String configFileName) {
        if (instance == null) {
            instance = new ProductDaoJdbc(configFileName);
        }
        return instance;
    }


    @Override
    public void add(Product product) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO product (name, default_price, currency, description, product_category_id, supplier_id, amount) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getDefaultPrice());
            statement.setString(3, product.getDefaultCurrency().toString());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name, default_price, currency, description, product_category_id, supplier_id, amount FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Product(
                    id,
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    ProductCategoryDaoJdbc
                            .getInstance()
                            .find(resultSet.getInt(5)),
                    SupplierDaoJdbc
                            .getInstance()
                            .find(resultSet.getInt(6)),
                    resultSet.getInt(7)
            );


        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    private List<Product> createProductList(ResultSet resultSet) throws SQLException {

        List<Product> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(
                    new Product(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            ProductCategoryDaoJdbc
                                    .getInstance()
                                    .find(resultSet.getInt(6)),
                            SupplierDaoJdbc
                                    .getInstance()
                                    .find(resultSet.getInt(7)),
                            resultSet.getInt(8)
                    )
            );
        }
        logger.info("All products queried successfully");
        return result;
    }

    @Override
    public List<Product> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, default_price, currency, description, product_category_id, supplier_id, amount FROM product";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            return createProductList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Product> getBy(Supplier supplier) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, default_price, currency, description, product_category_id, supplier_id, amount FROM product WHERE supplier_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, supplier.getId());
            ResultSet resultSet = statement.executeQuery();
            return createProductList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, default_price, currency, description, product_category_id, supplier_id, amount FROM product WHERE product_category_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productCategory.getId());
            ResultSet resultSet = statement.executeQuery();
            return createProductList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> getBy(String name) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, default_price, currency, description, product_category_id, supplier_id, amount FROM product WHERE name ILIKE '%' || ? || '%' ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return createProductList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        return null;
    }
}
