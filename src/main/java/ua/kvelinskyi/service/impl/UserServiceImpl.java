package ua.kvelinskyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.repository.UserRepository;
import ua.kvelinskyi.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        User saveUser = userRepository.saveAndFlush(user);
        return saveUser;
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User getByLoginAndName(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User editUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public List<Form39> getListForm39User(Integer idUser) {
        User user = getUserById(idUser);
        return user.getForm39List();
    }

}
