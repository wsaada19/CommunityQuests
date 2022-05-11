package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.event.Listener;

public class NPCInteractEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.MOB_Kill;

    public NPCInteractEvent(ActiveQuests activeQuests) {

        super(activeQuests);
    }

}
