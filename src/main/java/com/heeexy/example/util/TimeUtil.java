package com.heeexy.example.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static boolean isSameDate(Date dt1, Date dt2) {
		Calendar c1 = Calendar.getInstance();   
        Calendar c2 = Calendar.getInstance();   
        c1.setTime(dt1);   
        c2.setTime(dt2);   
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))   
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))   
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));   
    }
}
