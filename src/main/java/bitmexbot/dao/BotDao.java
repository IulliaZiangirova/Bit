package bitmexbot.dao;

import bitmexbot.model.Bot;
import bitmexbot.model.Order;
import bitmexbot.model.User;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class BotDao {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<List<Bot>> findByUserId(String id) {
        Session session = sessionFactory.openSession();
        Query<Bot> bots = session.createQuery("select b from Bot b where b.user.id =:id ", Bot.class);
        bots.setParameter("id", id);
        List<Bot> list = bots.list();
        return Optional.ofNullable(list);
    }

    public void save(Bot bot) {
        System.out.println("dsve");
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(bot);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Bot findById (String id){
        Session session = sessionFactory.openSession();
        Query<Bot> botQuery = session.createQuery("select u from User u where u.id = :id", Bot.class);
        botQuery.setParameter("id", id);
        Bot bot = botQuery.getSingleResult();
        return bot;
    }

    public void delete(Bot bot){
        Session session = sessionFactory.openSession();
        session.remove(bot);
    }


    public Optional<List<Bot>> findWorkingBot(String id) {
        Session session = sessionFactory.openSession();
        Query<Bot> bots = session.createQuery("select b from Bot b where b.workingIndicator = true and b.user.id =:id ", Bot.class);
        bots.setParameter("id", id);
        List<Bot> list = bots.list();
        return Optional.ofNullable(list);
    }

    public void updateWorking (String id, boolean isWorking){
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Bot botUpdate = session.find(Bot.class, id);
            botUpdate.setWorkingIndicator(isWorking);
            session.merge(botUpdate);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
