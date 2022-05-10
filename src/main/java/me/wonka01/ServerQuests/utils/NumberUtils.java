package me.wonka01.ServerQuests.utils;

import lombok.NonNull;

import java.text.DecimalFormat;

public interface NumberUtils {
    static @NonNull String decimals(double a) {
        DecimalFormat format = new DecimalFormat("0.#");
        return  format.format(a);
    }
}
