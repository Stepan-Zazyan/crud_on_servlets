package aston.service;

import aston.entity.User;
import aston.repository.JDBCUserRepository;

import java.util.Optional;

public class JDBCUserService implements UserService {

    JDBCUserRepository userRepository;

    public JDBCUserService(JDBCUserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public int findPassportsByUserJdbc(int id) {
        return userRepository.findPassportsByUserJdbc(id);
    }


}
