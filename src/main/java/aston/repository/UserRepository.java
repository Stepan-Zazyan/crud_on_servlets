package aston.repository;

import aston.entity.User;

import java.util.Optional;

public interface UserRepository {
    User create(User user);

    Optional<User> findById(int id);

    int findPassportsByUserJdbc(int id);
}
