package com.zy.cn.test;

import com.zy.cn.dao.UserDAO;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMybeis {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void testInsert() {
        userDAO.insert(new User(null, "长城", 18, new Date()));
    }

    @Test
    public void testDelete() {
        userDAO.delete(3);
    }

    @Test
    public void testFind() {
        User user = userDAO.find(4);
        System.out.println(user.getName());
        System.out.println("=====================");
        User user2 = userDAO.find(4);
        System.out.println("=====================");
        User user3 = userDAO.find(4);
    }

    @Test
    public void testquery() {
        List<User> users = userDAO.queryAll();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=====================");
        List<User> users1 = userDAO.queryAll();
        List<User> users2 = userDAO.queryAll();
        List<User> users3 = userDAO.queryAll();
        List<User> users4 = userDAO.queryAll();
        for (User user : users4) {
            System.out.println(user);
        }
    }
}
