package aston.repository;

import aston.entity.Passport;

import java.sql.*;
import java.util.Optional;

public class JDBCPassportRepository implements PassportRepository {

    private Connection cnn;

    public JDBCPassportRepository() {
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
    public Passport create(Passport passport) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "INSERT INTO aston.passports (number) values (?) ",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, passport.getNumber());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    passport.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passport;
    }

    @Override
    public Optional<Passport> findById(int id) {
        Passport passport = new Passport();
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT * from aston.passports WHERE id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    passport = new Passport(
                            resultSet.getInt("id"),
                            resultSet.getInt("number"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(passport);
    }

}
