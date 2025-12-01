package latihan.gradle.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import latihan.gradle.api.model.User;

@Service
public interface UserService {
    // Define service methods here
    public User createUser(User user);
    public User getUserById(Long id);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);
    public List<User> getAllUsers();
}