package aston.repository;

import aston.entity.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JDBCPostRepositoryTest {

    static Post post;

    private static JDBCPostRepository jdbcPostRepository;

    @BeforeAll
    public static void init() {
        jdbcPostRepository = new JDBCPostRepository();
        post = jdbcPostRepository.create(new Post(2, "test", "descTest"));
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var createdPost = jdbcPostRepository.findById(post.getId());
        assertThat(createdPost.getId()).isEqualTo(post.getId());
    }

    @Test
    public void whenCreateThenGetSame() {
        var createdPost = jdbcPostRepository.findById(post.getId());
        assertThat(createdPost).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    public void whenFindByTitleThenGetSame() {
        var createdPost = jdbcPostRepository.findByName(post.getTitle());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        jdbcPostRepository.delete(post.getId());
        var createdPost = jdbcPostRepository.findById(post.getId());
        var expected = new Post();
        assertThat(createdPost).isEqualTo(expected);
    }

    @Test
    public void whenUpdateThenGetUpdated() {
        Post updatedPost = jdbcPostRepository.create(new Post(2, "updatedTest", "descTest"));
        boolean result = jdbcPostRepository.update(updatedPost);
        assertThat(result).isTrue();
    }

}