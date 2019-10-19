package Database;

import Entity.User;

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

    public boolean findUserAndPasswordFromDatabase(String name, int password) {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("find_user_and_pasword_from_database");
        query.setParameter("name", name);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        closeSessionAndTransaction();
        return user!=null;
    }
}
