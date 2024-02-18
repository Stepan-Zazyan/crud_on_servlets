package aston.service;

import aston.dto.PostDto;
import aston.entity.Passport;
import aston.entity.Post;
import aston.entity.User;
import aston.mapper.PostMapper;
import aston.repository.PostRepository;

import java.util.List;

public class JDBCPostService implements PostService {
    private final PostRepository postRepository;

    private final UserService userService;

    public JDBCPostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Post create(Post post) {
        return postRepository.create(post);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Post findByName(String name) {
        return postRepository.findByName(name);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }
    @Override
    public void delete(int id) {
        postRepository.delete(id);
    }
    @Override
    public PostDto findPostDTOByName(int id, String title) {
        Post postObj = postRepository.findByName(title);
        User user = userService.findById(id).get();
        Passport passport = new Passport();
        passport.setNumber(userService.findPassportsByUserJdbc(user.getId()));
        return new PostMapper().getDto(postObj, user, passport);
    }
}
