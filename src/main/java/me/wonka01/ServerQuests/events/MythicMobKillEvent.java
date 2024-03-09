package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;

import java.util.List;

public class MythicMobKillEvent extends QuestListener implements Listener {

    public MythicMobKillEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onKillMythicMobEvent(MythicMobDeathEvent event) {
        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.MYTHIC_MOB);
        LivingEntity entity = event.getKiller();
        if (!(entity instanceof Player)) {
            return;
        }

        for (QuestController controller : controllers) {
            List<String> mythicMobTypes = controller.getEventConstraints().getMobNames();
            if (mythicMobTypes.isEmpty() || mythicMobTypes.contains(event.getMobType().getInternalName())) {
                updateQuest(controller, (Player) event.getKiller(), 1);
            }
        }
    }
}
