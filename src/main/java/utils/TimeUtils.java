package utils;

public class TimeUtils {
    public static long playedToMilliseconds(int hours, int minutes, int seconds) {
        return (hours * 60 * 60 * 1000L) +
                (minutes * 60 * 1000) +
                (seconds * 1000);
    }

    public static int millisToSeconds(long millis) {
        return (int) (millis / 1000) % 60;
    }

    public static int millisToMinutes(long millis) {
        return (int) ((millis / (1000 * 60)) % 60);
    }

    public static int millisToHours(long millis) {
        return (int) (millis / (1000 * 60 * 60));
    }
}
