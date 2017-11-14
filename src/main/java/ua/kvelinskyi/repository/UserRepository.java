package ua.kvelinskyi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kvelinskyi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);



}
