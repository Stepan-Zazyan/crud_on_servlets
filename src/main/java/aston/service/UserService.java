package aston.service;

import aston.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(int id);

    int findPassportsByUserJdbc(int id);
}
