package de.patrickgornig.recipeorganizer.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType
public class YearWeek {


    int year;
    int week;

    public YearWeek(int year, int week){
        this.year = year;
        this.week = week;
    };

    public YearWeek(int yearWeek){
        String s = String.valueOf(yearWeek);
        year = Integer.parseInt(s.substring(1, 4));
        week = Integer.parseInt(s.substring(5, s.length()));
    }

    public YearWeek(LocalDate localDate){
        int week = localDate.get(WeekFields.ISO.weekOfWeekBasedYear());
        if( week == 52 && localDate.getMonth() == Month.JANUARY){
            this.year = localDate.getYear()-1;
        } else {
            this.year = localDate.getYear();
        }
        this.week = week;
    }
    
    public int getYear() {
        return year;
    }

    public int getWeek() {
        return week;
    }

    public String toString(){
        return ""+year+week;
    }

}
