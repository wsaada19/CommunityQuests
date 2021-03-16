package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class TameEvent extends QuestListener implements Listener {
    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.TAME;

    public TameEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onTameEvent(EntityTameEvent tameEvent) {
        if (tameEvent.getOwner() instanceof Player) {
            for (QuestController controller : activeQuests.getActiveQuestsList()) {
                if (controller.getListenerType().equals(TYPE)) {
                    updateQuest(controller, (Player) tameEvent.getOwner(), 1);
                }
            }
            removedFinishedQuests();
        }
    }
}
