package latihan.gradle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import latihan.gradle.api.model.User;

public interface UserRepository extends JpaRepository <User, Long>  {
    Optional<User> findByUsername(String username);
}
