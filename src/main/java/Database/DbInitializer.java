package Database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class DbInitializer {
    private SessionFactory sessionFactory;

    protected Session session;
    protected Transaction transaction;

    public DbInitializer(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    protected void openSessionAndTransaction(){
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    protected void closeSessionAndTransaction(){
        transaction.commit();
        session.close();
    }

}
