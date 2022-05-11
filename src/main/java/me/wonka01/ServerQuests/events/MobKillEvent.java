package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.utils.EntityUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class MobKillEvent extends QuestListener implements Listener {

    public MobKillEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void OnPlayerKillMob(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer == null) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.MOB_KILL);
        for (QuestController controller : controllers) {
            List<String> mobTypes = controller.getEventConstraints().getMobNames();
            if (mobTypes.isEmpty() || EntityUtil.containsEntity(entity.getType().name(), mobTypes)) {
                updateQuest(controller, killer, 1);
            }
        }
    }
}
