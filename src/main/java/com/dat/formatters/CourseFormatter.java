package com.dat.formatters;

import com.dat.pojo.Course;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class CourseFormatter implements Formatter<Course> {

    @Override
    public Course parse(String text, Locale locale) throws ParseException {
        return new Course(Integer.parseInt(text));
    }

    @Override
    public String print(Course object, Locale locale) {
        return object.getId().toString();
    }
}