package todday.funny.seoulcatcher.util;

public class NullFilter {
    public static String check(String text) {
        return text != null ? text : "";
    }

    public static Integer check(Integer integer) {
        return integer != null ? integer : 0;
    }
}
