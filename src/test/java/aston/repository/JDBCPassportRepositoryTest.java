package aston.repository;

import aston.entity.Passport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JDBCPassportRepositoryTest {

    static Passport passport;

    private static JDBCPassportRepository jdbcPassportRepository;

    @BeforeAll
    public static void init() {
        jdbcPassportRepository = new JDBCPassportRepository();
        passport = jdbcPassportRepository.create(new Passport(1, 111));
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var createdUser = jdbcPassportRepository.findById(passport.getId()).get();
        assertThat(createdUser.getId()).isEqualTo(passport.getId());
    }

    @Test
    public void whenCreateThenGetSame() {
        var createdPost = jdbcPassportRepository.findById(passport.getId());
        assertThat(createdPost.get()).usingRecursiveComparison().isEqualTo(passport);
    }

}