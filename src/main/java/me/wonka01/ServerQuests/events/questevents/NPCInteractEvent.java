package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.event.Listener;

public class NPCInteractEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.MOB_Kill;

    public NPCInteractEvent(ActiveQuests activeQuests){

        super(activeQuests);
    }

}
