package Database;

import Entity.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;

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

    public boolean validateCredentials(String username, String password) throws NoResultException {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("validate_username_password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        closeSessionAndTransaction();
        return user != null;
    }
}

