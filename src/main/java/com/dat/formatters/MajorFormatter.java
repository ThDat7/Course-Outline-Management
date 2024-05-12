package com.dat.formatters;

import com.dat.pojo.Major;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class MajorFormatter implements Formatter<Major> {

    @Override
    public Major parse(String text, Locale locale) throws ParseException {
        return new Major(Integer.parseInt(text));
    }

    @Override
    public String print(Major object, Locale locale) {
        return object.getId().toString();
    }
}
