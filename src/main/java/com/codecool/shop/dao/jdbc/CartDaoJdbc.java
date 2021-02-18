package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.config.DatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {

    DataSource dataSource;

    private static CartDaoJdbc instance = null;

    private CartDaoJdbc() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dataSource = dbManager.getDataSource();
    }

    private CartDaoJdbc(String configFileName) {
        DatabaseManager dbManager = DatabaseManager.getInstance(configFileName);
        dataSource = dbManager.getDataSource();
    }

    public static CartDaoJdbc getInstance() {
        if (instance == null) {
            instance = new CartDaoJdbc();
        }
        return instance;
    }

    public static CartDaoJdbc getInstance(String configFileName) {
        if (instance == null) {
            instance = new CartDaoJdbc(configFileName);
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO cart (user_id) VALUES (?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, cart.getUser().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            cart.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int id) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT user_id FROM cart  WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Cart(
                    id,
                    UserDaoJdbc
                            .getInstance()
                            .find(resultSet.getInt(1))
            );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM cart WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Cart> createCartList(ResultSet resultSet) throws SQLException {

        List<Cart> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(
                    new Cart(
                            UserDaoJdbc
                                    .getInstance()
                                    .find(resultSet.getInt(1))
                    )
            );
        }
        return result;
    }

    @Override
    public List<Cart> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, user_id FROM cart";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            return createCartList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSize() {
        return -1;
    }
}
