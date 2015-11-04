package model;

/**
 * Created by brian on 10/18/15.
 */
public class Time {
    private int millis;

    public static Time createTimeFromBTUs(int btus) {
        Time t = new Time();
        t.millis = btus * 15;
        return t;
    }

    public static Time createTimeFromPTUs(int btus) {
        Time t = new Time();
        t.millis = btus * 15;
        return t;
    }
//
//    public Time add(Time t1, Time t2) {
//
//    }
}
