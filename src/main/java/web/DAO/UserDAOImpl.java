package web.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Repository;
import web.config.DatabaseConnection;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public UserDAOImpl() {
    }


    public User showById(int id) {
        return getEntityManager().find(User.class, id);
    }

    public void update(User user) {
        getEntityManager().merge(user);
    }

    public void save(User user) {
        getEntityManager().persist(user);
    }

    public void delete(int id) {
        getEntityManager()
                .createQuery("delete from User where id =: id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<User> getAllUsers() {
        return getEntityManager().createQuery("From User ").getResultList();
    }
}
