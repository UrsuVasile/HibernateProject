package Database;

import CustomExceptions.UserIdNotSet;
import Entity.User;
import Service.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UserDao extends DbInitializer {

    public void insertUser(User user) {
        openSessionAndTransaction();
        session.persist(user);
        closeSessionAndTransaction();
    }

    public User findUserById(int id) {
        openSessionAndTransaction();
        User user = session.find(User.class, id);
        closeSessionAndTransaction();
        return user;
    }

    public boolean findUserAndPasswordFromDatabase(String name, String password) {
        Service service = new Service();
        openSessionAndTransaction();
        Query query = session.createNamedQuery("find_user_and_pasword_from_database");
        query.setParameter("username", name);

        String saltedPassword = "bubulici" + password;
        String hashedPassword = service.generateHash(saltedPassword);
        query.setParameter("password", hashedPassword);
        try {
            User user = (User) query.getSingleResult();
            closeSessionAndTransaction();
            return true;
        } catch (NoResultException e) {
            closeSessionAndTransaction();
            return false;
        }
    }

    public List<User> showAllUsers() {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("show_all_users");
        List<User> users = query.getResultList();
        closeSessionAndTransaction();
        return users;
    }

    public void updateUserRol(User user) {
        if (user.getId() != 0) {
            openSessionAndTransaction();
            session.update(user);
            closeSessionAndTransaction();
        } else {
            throw new UserIdNotSet();
        }
    }

    public String findUserRol(String userName, String password) {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("find_user_and_pasword_from_database");
        query.setParameter("username", userName);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        String rol = user.getRol();
        closeSessionAndTransaction();
        return rol;
    }

    public boolean verifyIfExistUsers() {
        boolean user = true;
        openSessionAndTransaction();
        Query query = session.createNamedQuery("show_all_users");
        closeSessionAndTransaction();
        return user;
    }

}
