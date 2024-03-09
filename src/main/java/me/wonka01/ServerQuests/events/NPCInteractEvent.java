package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.event.Listener;

public class NPCInteractEvent extends QuestListener implements Listener {

    public NPCInteractEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }
}
