package latihan.gradle.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import latihan.gradle.api.model.Users;

@Service
public interface UserService {
    // Define service methods here
    public Users createUser(Users user);
    public Users getUserById(Long id);
    public Users updateUser(Long id, Users user);
    public void deleteUser(Long id);
    public List<Users> getAllUsers();
}