package com.midnight.engage;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by adi on 7/21/16.
 */
public class SomeRandomeClass {
    public static List<String> list = new ArrayList<>();
    public static void AddToList(String key , String value){
        if (list.size() == 0)
        list.add(key + "|12:12|" +value);
        else
            list.add("|,',|"+key + "|12:12|" +value);

    }
    public static String GetData() {
       String data = "";

        for (String x : list)
        data = data + x;

        TimeZone london = TimeZone.getTimeZone("Europe/London");
        long now = System.currentTimeMillis();
        now =  now + london.getOffset(now);
        if (list.size() == 0 )
            data = "api_key|12:12|kasld1>!<123kml1";
else
        data = data + "|,',|api_key|12:12|kasld1>!<123kml1";
        data = data + "|,',|request_time|12:12|" + String.valueOf(now).substring(0 , String.valueOf(now).length() - 3);
        list.clear();

        try {
            return new Encrypt().encrypt(data);
        } catch (Exception e) {
            return "error";
        }
    }
}
