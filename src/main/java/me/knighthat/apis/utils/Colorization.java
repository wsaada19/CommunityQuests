package me.knighthat.apis.utils;

import lombok.NonNull;
import org.bukkit.ChatColor;

public interface Colorization {

    default @NonNull String color(@NonNull String a) {
        return ChatColor.translateAlternateColorCodes('&', a);
    }

    default @NonNull String strip(@NonNull String a) {
        return ChatColor.stripColor(color(a));
    }
}
