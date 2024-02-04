package bitmexbot.dao;

import bitmexbot.model.Order;
import bitmexbot.model.OrderStatus;
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


//    public void update (Order order){
//        try (Session session = sessionFactory.openSession();) {
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.m(order);
//            transaction.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void merge (Order order){
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Order orderUpdate = session.find(Order.class, order.getOrderID());
            orderUpdate.setWorkingIndicator(false);
            orderUpdate.setOrdStatus(OrderStatus.CANCELED);
            session.merge(orderUpdate);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void merge1 (Order order){
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Order orderUpdate = session.find(Order.class, order.getOrderID());
            orderUpdate.setWorkingIndicator(false);
            orderUpdate.setOrdStatus(OrderStatus.FILLED);
            session.merge(orderUpdate);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
