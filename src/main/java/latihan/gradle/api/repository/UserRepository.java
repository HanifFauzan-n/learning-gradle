package latihan.gradle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import latihan.gradle.api.model.Users;

public interface UserRepository extends JpaRepository <Users, Long>  {
    Optional<Users> findByUsername(String username);
}
