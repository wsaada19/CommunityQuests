package me.wonka01.ServerQuests.utils;

import java.util.List;

public class MaterialUtil {
    public static boolean containsMaterial(String material, List<String> materials) {
        for (String targetMaterial : materials) {
            if (material.toUpperCase().contains(targetMaterial.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
