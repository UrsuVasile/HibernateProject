package Database;

import Entity.User;

public class UserDao extends DbInitializer{

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
}
