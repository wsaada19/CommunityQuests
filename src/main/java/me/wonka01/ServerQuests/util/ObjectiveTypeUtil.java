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
        } else if (eventType.equalsIgnoreCase("craftitem")) {
            return ObjectiveType.CRAFT_ITEM;
        } else if (eventType.equalsIgnoreCase("consumeitem")) {
            return ObjectiveType.CONSUME_ITEM;
        } else if (eventType.equalsIgnoreCase("enchantitem")) {
            return ObjectiveType.ENCHANT_ITEM;
        } else if (eventType.equalsIgnoreCase("money")) {
            return ObjectiveType.GIVE_MONEY;
        }
        return null;
    }

    public static Material getEventTypeDefaultMaterial(ObjectiveType eventType) {
        if (eventType == ObjectiveType.MOB_Kill) {
            return Material.DIAMOND_SWORD;
        } else if (eventType == ObjectiveType.CATCH_FISH) {
            return Material.FISHING_ROD;
        } else if (eventType == ObjectiveType.PLAYER_KILL) {
            return Material.CHAINMAIL_CHESTPLATE;
        } else if (eventType == ObjectiveType.BLOCK_BREAK) {
            return Material.DIAMOND_PICKAXE;
        } else if (eventType == ObjectiveType.PROJ_KILL) {
            return Material.BOW;
        } else if (eventType == ObjectiveType.BLOCK_PLACE) {
            return Material.DIRT;
        } else if (eventType == ObjectiveType.SHEAR) {
            return Material.SHEARS;
        } else if (eventType == ObjectiveType.TAME) {
            return Material.BONE;
        } else if (eventType == ObjectiveType.MILK_COW) {
            return Material.MILK_BUCKET;
        } else if (eventType == ObjectiveType.GUI) {
            return Material.CHEST;
        } else if (eventType == ObjectiveType.CRAFT_ITEM) {
            return Material.CHAINMAIL_HELMET;
        } else if (eventType == ObjectiveType.CONSUME_ITEM) {
            return Material.APPLE;
        } else if (eventType == ObjectiveType.ENCHANT_ITEM) {
            return Material.GOLDEN_APPLE;
        } else if (eventType == ObjectiveType.GIVE_MONEY) {
            return Material.EMERALD;
        }
        return null;
    }
}
