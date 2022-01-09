package de.patrickgornig.recipeorganizer.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Util {

    public static String getYearWeekFromLocalDate(LocalDate localDate){
        int week = localDate.get(WeekFields.ISO.weekOfWeekBasedYear());
        if( week == 52 && localDate.getMonth() == Month.JANUARY){
            return localDate.getYear()-1 + week + "";
        } else {
            return localDate.getYear() + week + "";
        }
    }


    public static LocalDate getFirstDayOfWeek(int year, int weekNumber) {
        return getFirstDayOfWeek(year,weekNumber,Locale.GERMANY);
    } 

    public static LocalDate getFirstDayOfWeek(int year, int weekNumber, Locale locale) {
        return LocalDate
                .of(year, 2, 1)
                .with(WeekFields.of(locale).getFirstDayOfWeek())
                .with(WeekFields.of(locale).weekOfWeekBasedYear(), weekNumber);
    } 
    
}
