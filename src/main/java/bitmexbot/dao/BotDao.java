package bitmexbot.dao;

import bitmexbot.model.Bot;
import bitmexbot.model.Order;
import bitmexbot.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class BotDao {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<List<Bot>> findByUserId(String id){
        Session session = sessionFactory.openSession();
        Query<Bot> bots = session.createQuery("select b from Bot b where b.user =:id ", Bot.class);
        bots.setParameter("id", id);
        List<Bot> list = bots.list();
        return Optional.ofNullable(list);
    }
}
