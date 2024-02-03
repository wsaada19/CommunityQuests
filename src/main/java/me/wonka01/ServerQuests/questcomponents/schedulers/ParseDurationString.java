package me.wonka01.ServerQuests.questcomponents.schedulers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseDurationString {
    public static int parseStringToSeconds(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        Pattern pattern = Pattern.compile("(?:(\\d+)d)?(?:(\\d+)h)?(?:(\\d+)m)?(?:(\\d+)s)?");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String daysString = matcher.group(1);
            String hoursString = matcher.group(2);
            String minutesString = matcher.group(3);
            String secondsString = matcher.group(4);

            int days = daysString != null ? Integer.parseInt(daysString) : 0;
            int hours = hoursString != null ? Integer.parseInt(hoursString) : 0;
            int minutes = minutesString != null ? Integer.parseInt(minutesString) : 0;
            int seconds = secondsString != null ? Integer.parseInt(secondsString) : 0;
            return (days * 24 * 60 * 60) + (hours * 60 * 60) + (minutes * 60) + seconds;
        }
        return 0;
    }

    public static String formatSecondsToString(int seconds) {
        int days = seconds / (24 * 60 * 60);
        int hours = (seconds % (24 * 60 * 60)) / (60 * 60);
        int minutes = (seconds % (60 * 60)) / 60;
        int remainingSeconds = seconds % 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (remainingSeconds > 0) {
            sb.append(remainingSeconds).append("s");
        }
        return sb.toString();
    }
}
