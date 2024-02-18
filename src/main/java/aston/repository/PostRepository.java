package aston.repository;

import aston.entity.Post;

import java.util.List;

public interface PostRepository {

    Post create(Post post);

    List<Post> getAll();

    Post findById(int id);

    Post findByName(String name);

    boolean update(Post post);

    void delete(int id);
}
