package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.DAO.UserDAO;
import web.DAO.UserDAOImpl;
import web.models.User;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserDAO userDAO = new UserDAOImpl();

    @Autowired
    public void setUserDao(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public User showById(int id) {
        return userDAO.showById(id);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void delete(int id) {
        userDAO.delete(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
