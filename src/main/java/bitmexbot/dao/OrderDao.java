package bitmexbot.dao;

import bitmexbot.model.Order;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save (Order order){
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(order);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
