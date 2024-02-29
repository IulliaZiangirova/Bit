package bitmexbot.dao;

import bitmexbot.model.Order;
import bitmexbot.model.User;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDao {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(User user) {
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findById (String id){
        Session session = sessionFactory.openSession();
        Query<User> players = session.createQuery("select u from User u where u.id = :id", User.class);
        players.setParameter("id", id);
        User singleResultOrNull = players.getSingleResultOrNull();
        return Optional.ofNullable(singleResultOrNull);
    }

    public Optional<User> findByName (String name){
        Session session = sessionFactory.openSession();
        Query<User> players = session.createQuery("select u from User u where u.name = :name", User.class);
        players.setParameter("name", name);
        User singleResultOrNull = players.getSingleResultOrNull();
        return Optional.ofNullable(singleResultOrNull);
    }
}
