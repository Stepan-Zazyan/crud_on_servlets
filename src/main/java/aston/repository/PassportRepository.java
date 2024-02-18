package aston.repository;

import aston.entity.Passport;

import java.util.Optional;

public interface PassportRepository {

    Passport create(Passport passport);

    Optional<Passport> findById(int id);
}
