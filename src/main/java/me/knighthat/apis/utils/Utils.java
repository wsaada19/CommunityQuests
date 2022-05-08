package me.knighthat.apis.utils;

import lombok.NonNull;

import java.text.MessageFormat;

public interface Utils {

    static @NonNull String decimals(double a, int b) {

        String format = MessageFormat.format("%.{0}f", b);
        return String.format(format, a);
    }
}
