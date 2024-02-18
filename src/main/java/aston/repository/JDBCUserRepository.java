package aston.repository;

import aston.entity.User;

import java.sql.*;
import java.util.Optional;

public class JDBCUserRepository implements UserRepository {

    private Connection cnn;

    public JDBCUserRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            cnn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "1234"
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User create(User user) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "INSERT INTO aston.users (name, login, password) values (?, ?, ?) ",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        User user = new User();
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT * from aston.users WHERE id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("login"),
                            resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(user);
    }

    @Override
    public int findPassportsByUserJdbc(int id) {
        int passportNumber = 0;
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT p.number FROM aston.passports "
                             + "as p JOIN aston.users_passports as up "
                             + "ON p.id = up.passport_id WHERE up.user_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    passportNumber = resultSet.getInt("number");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passportNumber;
    }
}
