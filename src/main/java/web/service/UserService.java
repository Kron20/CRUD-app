package web.service;


import web.models.User;

import java.util.List;

public interface UserService {
    User showById(int id);

    void update(User user);

    void save(User user);

    void delete(int id);

    List<User> getAllUsers();
}
