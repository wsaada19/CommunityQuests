package me.knighthat.apis.utils;

import org.jetbrains.annotations.Nullable;

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
}
