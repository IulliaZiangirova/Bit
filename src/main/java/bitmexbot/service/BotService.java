package bitmexbot.service;

import bitmexbot.dao.BotDao;
import bitmexbot.model.Bot;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BotService {

    private static final BotDao botDao = new BotDao();
    private static Map<Bot, BotExecutor> botToBotExecutor= new HashMap<>();


    public List<Bot> findByUserId (String id){
        return botDao.findByUserId(id).get();
    }

    public Bot findById (String id){
        return botDao.findById(id);
    }


    public void create (Bot bot){
        botDao.save(bot);
    }

    public void start(String id){
        botDao.updateWorking(id, true);
        Bot bot = findById(id);
        BotExecutor botExecutor = new BotExecutor(bot);
        botToBotExecutor.put(bot, botExecutor);
        botExecutor.start();
    }

    public void stop(String id){
        Bot bot = findById(id);
        BotExecutor botExecutor = botToBotExecutor.get(bot);
        botExecutor.stop();
        botDao.updateWorking(id, false);
    }

    public void delete(Bot bot){
        botDao.delete(bot);
    }

    public boolean hasWorkingBot(String userId){
        return botDao.findWorkingBot(userId).isPresent();
    }



}
