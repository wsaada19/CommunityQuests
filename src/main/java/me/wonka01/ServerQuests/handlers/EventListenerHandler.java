package me.wonka01.ServerQuests.handlers;

import org.bukkit.Material;

public class EventListenerHandler {

    public enum EventListenerType {
        MOB_Kill,
        CATCH_FISH,
        PLAYER_KILL,
        BLOCK_BREAK,
        PROJ_KILL,
        BLOCK_PLACE,
        SHEAR,
        TAME,
        GUI,
        MILK_COW
    }

    public static EventListenerType parseEventTypeFromString(String eventType) {
        if (eventType.equalsIgnoreCase("mobkill")) {
            return EventListenerType.MOB_Kill;
        } else if (eventType.equalsIgnoreCase("catchfish")) {
            return EventListenerType.CATCH_FISH;
        } else if (eventType.equalsIgnoreCase("playerkill")) {
            return EventListenerType.PLAYER_KILL;
        } else if (eventType.equalsIgnoreCase("blockbreak")) {
            return EventListenerType.BLOCK_BREAK;
        } else if (eventType.equalsIgnoreCase("projectilekill")) {
            return EventListenerType.PROJ_KILL;
        } else if (eventType.equalsIgnoreCase("blockPlace")) {
            return EventListenerType.BLOCK_PLACE;
        } else if (eventType.equalsIgnoreCase("shear")) {
            return EventListenerType.SHEAR;
        } else if (eventType.equalsIgnoreCase("tame")) {
            return EventListenerType.TAME;
        } else if (eventType.equalsIgnoreCase("milkcow")) {
            return EventListenerType.MILK_COW;
        } else if (eventType.equalsIgnoreCase("donate")) {
            return EventListenerType.GUI;
        }
        return null;
    }

    public static Material getEventTypeDefaultMaterial(EventListenerType eventType) {
        if (eventType == EventListenerType.MOB_Kill) {
            return Material.ZOMBIE_HEAD;
        } else if (eventType == EventListenerType.CATCH_FISH) {
            return Material.FISHING_ROD;
        } else if (eventType == EventListenerType.PLAYER_KILL) {
            return Material.PLAYER_HEAD;
        } else if (eventType == EventListenerType.BLOCK_BREAK) {
            return Material.DIAMOND_PICKAXE;
        } else if (eventType == EventListenerType.PROJ_KILL) {
            return Material.BOW;
        } else if (eventType == EventListenerType.BLOCK_PLACE) {
            return Material.GRASS_BLOCK;
        } else if (eventType == EventListenerType.SHEAR) {
            return Material.SHEARS;
        } else if (eventType == EventListenerType.TAME) {
            return Material.BONE;
        } else if (eventType == EventListenerType.MILK_COW) {
            return Material.MILK_BUCKET;
        } else if (eventType == EventListenerType.GUI) {
            return Material.CHEST;
        }
        return null;
    }
}
