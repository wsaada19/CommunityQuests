package me.wonka01.ServerQuests.handlers;

public class EventListenerHandler {

    public enum EventListenerType {
        MOB_Kill,
        CATCH_FISH,
        PLAYER_KILL,
        BLOCK_BREAK,
        PROJ_KILL,
        BLOCK_PLACE,
        SHEAR,
        TAME
    }

    public static EventListenerType parseEventTypeFromString(String eventType)
    {
        if(eventType.equalsIgnoreCase("mobkill")){
            return EventListenerType.MOB_Kill;
        } else if(eventType.equalsIgnoreCase("catchfish"))
        {
            return EventListenerType.CATCH_FISH;
        } else if(eventType.equalsIgnoreCase("playerkill")){
            return EventListenerType.PLAYER_KILL;
        } else if(eventType.equalsIgnoreCase("blockbreak"))
        {
            return EventListenerType.BLOCK_BREAK;
        } else if(eventType.equalsIgnoreCase("projectilekill"))
        {
            return EventListenerType.PROJ_KILL;
        } else if(eventType.equalsIgnoreCase("blockPlace")){
            return EventListenerType.BLOCK_PLACE;
        } else if(eventType.equalsIgnoreCase("shear")){
            return EventListenerType.SHEAR;
        } else if(eventType.equalsIgnoreCase("tame")){
            return EventListenerType.TAME;
        }
        return null;
    }
}
