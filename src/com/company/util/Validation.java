package com.company.util;

import java.util.Date;
import java.util.regex.Pattern;

public class Validation {
    private static String stringPat = "[а-яА-Я]+";
    public static boolean checkFIOFields(String string){
        if(Pattern.compile(stringPat).matcher(string).matches())
            return true;
        else return false;
    }

    public static boolean checkDateFields(Date date){
        if(new Date().compareTo(date) == 1)
            return true;
        else return false;
    }
}
