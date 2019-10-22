package Database;

import Entity.User;
import Service.Service;

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

    public boolean findUserAndPasswordFromDatabase(String name, String password)  {
        Service service = new Service();
        openSessionAndTransaction();
        Query query = session.createNamedQuery("find_user_and_pasword_from_database");
        query.setParameter("username", name);
        query.setParameter("password", service.md5(password));
        try {
            User user = (User) query.getSingleResult();
            closeSessionAndTransaction();
            return true;
        } catch (NoResultException e) {
            closeSessionAndTransaction();
            return false;
        }
    }
}
