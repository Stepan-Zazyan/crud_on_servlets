package aston.service;

import aston.dto.PostDto;
import aston.entity.Post;

import java.util.List;

public interface PostService {
    Post create(Post post);

    List<Post> getAll();

    Post findById(int id);

    Post findByName(String name);

    boolean update(Post post);

    void delete(int id);

    PostDto findPostDTOByName(int id, String title);
}
