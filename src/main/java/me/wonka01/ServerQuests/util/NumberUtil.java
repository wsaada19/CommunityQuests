package me.wonka01.ServerQuests.util;

import java.text.DecimalFormat;

public class NumberUtil {
    public static String getNumberDisplay(double amount) {
        DecimalFormat format = new DecimalFormat("0.#");
        return  format.format(amount);
    }
}
