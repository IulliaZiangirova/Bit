package bitmexbot.dao;

import bitmexbot.model.Order;
import bitmexbot.model.User;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDao {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(User user) {  //надо переделать
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
