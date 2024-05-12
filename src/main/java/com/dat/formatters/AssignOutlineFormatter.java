package com.dat.formatters;

import com.dat.pojo.AssignOutline;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class AssignOutlineFormatter implements Formatter<AssignOutline> {

    @Override
    public AssignOutline parse(String text, Locale locale) throws ParseException {
        return new AssignOutline(Integer.parseInt(text));
    }

    @Override
    public String print(AssignOutline object, Locale locale) {
        return object.getId().toString();
    }
}