package bitmexbot.service;

import bitmexbot.dao.UserDao;
import bitmexbot.exceptions.NotFoundException;
import bitmexbot.model.User;

public class UserService {

    private static final UserDao userDao = new UserDao();

    public User findById(String id){
        if (userDao.findById(id).isPresent()){
            return userDao.findById(id).get();
        }else throw new NotFoundException("User is not found");
    }

    public User findByName(String name){
        if (userDao.findByName(name).isPresent()){
            return userDao.findByName(name).get();
        }else throw new NotFoundException("User is not found");
    }

    public boolean checkUser(String userName, String password){
        User user;
        if (userDao.findByName(userName).isPresent()){
            user = userDao.findByName(userName).get();
        }else throw new NotFoundException("User is not found");
        if (user.getPassword().equals(password)){
            return true;
        }
        else return false;
    }

    public void save (User user){
        userDao.save(user);
    }


}
