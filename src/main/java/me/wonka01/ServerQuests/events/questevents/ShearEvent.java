package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.List;

public class ShearEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.SHEAR;

    public ShearEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onSheer(PlayerShearEntityEvent shearEvent) {
        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for(QuestController controller : controllers) {
            updateQuest(controller, shearEvent.getPlayer(), 1);
        }
    }
}
