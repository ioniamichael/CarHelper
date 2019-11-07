package com.enjoyapp.carhelper.Models;

import java.util.Calendar;
import java.util.Date;

public class Greetings {

    public Greetings() {
    }

    public String getGreetings() {
        if (getTime() >= 6 && getTime() < 12) {
            return ("בוקר טוב");
        }else if (getTime() >= 12 && getTime() < 15) {
            return ("צהריים טובים");
        }
        else if (getTime() >= 15 && getTime() < 18) {
            return ("אחה״צ טובים");
        } else if (getTime() >= 18 && getTime() < 22) {
            return ("ערב טוב");
        } else {
            return ("לילה טוב");
        }
    }

    public int getTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return hours;
    }

}
