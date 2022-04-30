package me.wonka01.ServerQuests.util;

import java.util.List;

public class EntityUtil {
    public static boolean containsEntity(String entity, List<String> entityList) {
        for (String en : entityList) {
            if (en.equalsIgnoreCase(entity)) {
                return true;
            }
        }
        return false;
    }
}
