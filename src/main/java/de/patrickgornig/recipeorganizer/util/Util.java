package de.patrickgornig.recipeorganizer.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;

public class Util {

    public static String getYearWeekFromLocalDate(LocalDate localDate){
        int week = localDate.get(WeekFields.ISO.weekOfWeekBasedYear());
        if( week == 52 && localDate.getMonth() == Month.JANUARY){
            return localDate.getYear()-1 + week + "";
        } else {
            return localDate.getYear() + week + "";
        }
    }
    
}
