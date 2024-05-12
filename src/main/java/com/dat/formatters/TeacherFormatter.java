package com.dat.formatters;

import com.dat.pojo.Teacher;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class TeacherFormatter implements Formatter<Teacher> {

    @Override
    public Teacher parse(String text, Locale locale) throws ParseException {
        return new Teacher(Integer.parseInt(text));
    }

    @Override
    public String print(Teacher object, Locale locale) {
        return object.getId().toString();
    }
}
