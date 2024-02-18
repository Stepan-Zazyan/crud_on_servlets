package aston.repository;

import aston.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JDBCUserRepositoryTest {

    static User user;
    private static JDBCUserRepository jdbcUserRepository;

    @BeforeAll
    public static void init() {
        jdbcUserRepository = new JDBCUserRepository();
        user = jdbcUserRepository.create(new User(11, "name", "login0", "password"));
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var createdUser = jdbcUserRepository.findById(user.getId()).get();
        assertThat(createdUser.getId()).isEqualTo(user.getId());
    }

    @Test
    public void whenCreateThenGetSame() {
        var createdPost = jdbcUserRepository.findById(user.getId());
        assertThat(createdPost.get()).usingRecursiveComparison().isEqualTo(user);
    }

}