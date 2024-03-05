package bitmexbot.service;

import bitmexbot.dao.BotDao;
import bitmexbot.model.Bot;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BotService {

    private final BotDao botDao;

    public List<Bot> findByUserId (String id){
        return botDao.findByUserId(id).get();
    }
}
