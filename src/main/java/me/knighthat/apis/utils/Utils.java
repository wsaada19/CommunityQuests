package me.knighthat.apis.utils;

import lombok.NonNull;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Utils {

    static <F, V> boolean contains(@Nullable Collection<F> from, @Nullable V value) {

        if (from == null || value == null)
            return false;

        if (from.contains(value))
            return true;

        Stream<String> toString = from.stream().map(Objects::toString).map(String::toUpperCase);
        return toString.collect(Collectors.toList()).contains(value.toString().toUpperCase(Locale.ROOT));
    }

    static <F, V> boolean contains(@Nullable F[] from, @Nullable V value) {

        List<F> fromList = new ArrayList<>(Arrays.asList(from));
        return contains(fromList, value);
    }

    static @NonNull String decimalToString(double a) {
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(a);
    }

    static int getCustomModelData(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return -1;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasCustomModelData()) {
            return meta.getCustomModelData();
        }
        return -1;
    }
}
