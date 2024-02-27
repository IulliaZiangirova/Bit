package bitmexbot.dao;

import bitmexbot.model.Order;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

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


    public void merge (Order order){
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Order orderUpdate = session.find(Order.class, order.getOrderID());
            orderUpdate.setWorkingIndicator(order.isWorkingIndicator());
            orderUpdate.setOrdStatus(order.getOrdStatus());
            session.merge(orderUpdate);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public List<Order> findSellOrders (){
//        Session session = sessionFactory.openSession();
//        Query<Order> orders = session.createQuery("select o from Order o where o.side = :side and o.workingIndicator = true ", Order.class);
//        orders.setParameter("side", "Sell");
//        List<Order> list = orders.list();
//        return list;
//    }

    public Optional<List<Order>> findSellOpenOrders(){
        Session session = sessionFactory.openSession();
        Query<Order> orders = session.createQuery("select o from Order o where o.side = 'Sell' and o.workingIndicator is true ", Order.class);
        List<Order> list = orders.list();
        return Optional.ofNullable(list);
    }


    public Optional<Order> findBuyOrderWithLastPrice (Double price){
        Session session = sessionFactory.openSession();
        Query<Order> order = session.createQuery("select o from Order o where o.price = :price and o.workingIndicator is true", Order.class);
        order.setParameter("price", price);
        Order singleResultOrNull = order.getSingleResultOrNull();
        return Optional.ofNullable(singleResultOrNull);

    }


}
