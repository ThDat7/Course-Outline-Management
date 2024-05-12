package com.dat.formatters;

import com.dat.pojo.Faculty;
import com.dat.pojo.Major;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class FacultyFormatter implements Formatter<Faculty> {

    @Override
    public Faculty parse(String text, Locale locale) throws ParseException {
        return new Faculty(Integer.parseInt(text));
    }

    @Override
    public String print(Faculty object, Locale locale) {
        return object.getId().toString();
    }
}
