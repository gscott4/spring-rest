package com.example.springrest.dao;

import com.example.springrest.model.UserPojo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UserPojoDao {

    private static List<UserPojo> users = new ArrayList<>();
    private static int usersCount = 2;

    static {
        users.add(new UserPojo(1, "Grayson", LocalDate.parse("1993-05-16")));
        users.add(new UserPojo(2, "Stuart", LocalDate.parse("1989-02-19")));
    }

    public List<UserPojo> findAll() {
        return users;
    }

    public UserPojo save(UserPojo user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public UserPojo findOne(int id) {
        for(UserPojo userPojo : users) {
            if(userPojo.getId() == id) {
                return userPojo;
            }
        }
        return null;
    }

    public UserPojo deleteById(int id) {
        Iterator<UserPojo> iterator = users.iterator();
        while(iterator.hasNext()) {
            UserPojo userPojo = iterator.next();
            if(userPojo.getId() == id) {
                users.remove(userPojo);
                return userPojo;
            }
        }
        return null;
    }

}
