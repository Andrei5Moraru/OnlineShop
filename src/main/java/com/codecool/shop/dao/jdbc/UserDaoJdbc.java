package com.codecool.shop.dao.jdbc;

import com.codecool.shop.config.DatabaseManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;

import com.codecool.shop.util.*;

public class UserDaoJdbc implements UserDao {

    DataSource dataSource;

    private static UserDaoJdbc instance = null;

    private UserDaoJdbc() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dataSource = dbManager.getDataSource();
    }

    private UserDaoJdbc(String configFileName) {
        DatabaseManager dbManager = DatabaseManager.getInstance(configFileName);
        dataSource = dbManager.getDataSource();
    }

    public static UserDaoJdbc getInstance() {
        if (instance==null) {
            instance = new UserDaoJdbc();
        }
        return instance;
    }

    public static UserDaoJdbc getInstance(String configFileName) {
        if (instance==null) {
            instance = new UserDaoJdbc(configFileName);
        }
        return instance;
    }

    public String getPass(String username){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT password FROM users WHERE email LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            };
            return resultSet.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(String email) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            };
            return new User(resultSet.getString("name"),resultSet.getString("email"), resultSet.getString("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void add(User user) throws Exception {

        Encrypt encrypt = Encrypt.getInstance();
        String hashedPassword = encrypt.encrypt(user.getPassword());

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO users (name, email ,password) VALUES (?,?,?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashedPassword);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}