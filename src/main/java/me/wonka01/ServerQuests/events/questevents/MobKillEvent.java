package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class MobKillEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.MOB_Kill;

    public MobKillEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void OnPlayerKillMob(EntityDeathEvent event) {

        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        String mobName = event.getEntity().getName();

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            List<String> mobTypes = controller.getEventConstraints().getMobNames();
            if (mobTypes.isEmpty() || mobTypes.contains(mobName)) {
                updateQuest(controller, killer, 1);
            }
        }
    }
}
