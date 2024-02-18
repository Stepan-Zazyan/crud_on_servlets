package aston.repository;

import aston.entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostRepository implements PostRepository {

    private Connection cnn;

    public JDBCPostRepository() {
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
    public Post create(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "INSERT INTO aston.posts (title, description) values (?, ?) ",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT * from aston.posts;")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(new Post(
                            resultSet.getString("title"),
                            resultSet.getString("description")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Post findById(int id) {
        Post post = new Post();
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT * from aston.posts WHERE id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    post = new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public Post findByName(String title) {
        Post post = new Post();
        try (PreparedStatement statement =
                     cnn.prepareStatement("SELECT * from aston.posts WHERE title = ?;")) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    post = new Post(
                            resultSet.getString("title"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public boolean update(Post post) {
        boolean result;
        try (PreparedStatement statement =
                     cnn.prepareStatement("UPDATE aston.posts SET title = ?, description = ? WHERE id = ?")) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setInt(3, post.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("DELETE FROM aston.posts WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
