package cuidevin.com.user.service.impl;

import cuidevin.com.user.dao.UserDao;
import cuidevin.com.user.entity.User;
import cuidevin.com.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Long countUser() {
        return userDao.count();
    }

    @Override
    public User findByID(Long id) {
        User user = userDao.findById(id).get();
        return user;
    }

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User checkRepeatUser(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }


    @Transactional
    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userDao.findById(id).get();
        BeanUtils.copyProperties(user, u);
        return userDao.save(u);
    }
}

