package com.dat.formatters;

import com.dat.pojo.User;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class UserFormatter implements Formatter<User> {

    @Override
    public User parse(String userId, Locale locale) throws ParseException {
        int id = Integer.parseInt(userId);
        return new User(id);
    }

    @Override
    public String print(User user, Locale locale) {
        return user.getId().toString();
    }
}
