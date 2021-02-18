package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DatabaseManager;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SupplierDaoJdbc implements SupplierDao {

    private static final Logger logger = LoggerFactory.getLogger(SupplierDaoJdbc.class);

    DataSource dataSource;

    private static SupplierDaoJdbc instance = null;

    private SupplierDaoJdbc() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dataSource = dbManager.getDataSource();
    }

    private SupplierDaoJdbc(String configFileName) {
        DatabaseManager dbManager = DatabaseManager.getInstance(configFileName);
        dataSource = dbManager.getDataSource();
    }

    public static SupplierDaoJdbc getInstance() {
        if (instance==null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    public static SupplierDaoJdbc getInstance(String configFileName) {
        if (instance==null) {
            instance = new SupplierDaoJdbc(configFileName);
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO supplier (name, description) VALUES (?, ?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            supplier.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier find(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT name, description FROM supplier WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Supplier(
                    id,
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM supplier WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }

    private List<Supplier> createSupplierList(ResultSet resultSet) throws SQLException {

        List<Supplier> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(
                    new Supplier(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return result;
    }

    @Override
    public List<Supplier> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, description FROM supplier";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            return createSupplierList(resultSet);
        } catch (SQLException e) {
            logger.debug("Exception {} occurred", e.toString());
            throw new RuntimeException(e);
        }
    }
}
