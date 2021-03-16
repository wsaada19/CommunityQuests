package me.wonka01.ServerQuests.util;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.Material;

public class ObjectiveTypeUtil {

    public static ObjectiveType parseEventTypeFromString(String eventType) {
        if (eventType.equalsIgnoreCase("mobkill")) {
            return ObjectiveType.MOB_Kill;
        } else if (eventType.equalsIgnoreCase("catchfish")) {
            return ObjectiveType.CATCH_FISH;
        } else if (eventType.equalsIgnoreCase("playerkill")) {
            return ObjectiveType.PLAYER_KILL;
        } else if (eventType.equalsIgnoreCase("blockbreak")) {
            return ObjectiveType.BLOCK_BREAK;
        } else if (eventType.equalsIgnoreCase("projectilekill")) {
            return ObjectiveType.PROJ_KILL;
        } else if (eventType.equalsIgnoreCase("blockPlace")) {
            return ObjectiveType.BLOCK_PLACE;
        } else if (eventType.equalsIgnoreCase("shear")) {
            return ObjectiveType.SHEAR;
        } else if (eventType.equalsIgnoreCase("tame")) {
            return ObjectiveType.TAME;
        } else if (eventType.equalsIgnoreCase("milkcow")) {
            return ObjectiveType.MILK_COW;
        } else if (eventType.equalsIgnoreCase("donate")) {
            return ObjectiveType.GUI;
        }
        return null;
    }

    public static Material getEventTypeDefaultMaterial(ObjectiveType eventType) {
        if (eventType == ObjectiveType.MOB_Kill) {
            return Material.ZOMBIE_HEAD;
        } else if (eventType == ObjectiveType.CATCH_FISH) {
            return Material.FISHING_ROD;
        } else if (eventType == ObjectiveType.PLAYER_KILL) {
            return Material.PLAYER_HEAD;
        } else if (eventType == ObjectiveType.BLOCK_BREAK) {
            return Material.DIAMOND_PICKAXE;
        } else if (eventType == ObjectiveType.PROJ_KILL) {
            return Material.BOW;
        } else if (eventType == ObjectiveType.BLOCK_PLACE) {
            return Material.GRASS_BLOCK;
        } else if (eventType == ObjectiveType.SHEAR) {
            return Material.SHEARS;
        } else if (eventType == ObjectiveType.TAME) {
            return Material.BONE;
        } else if (eventType == ObjectiveType.MILK_COW) {
            return Material.MILK_BUCKET;
        } else if (eventType == ObjectiveType.GUI) {
            return Material.CHEST;
        }
        return null;
    }
}
