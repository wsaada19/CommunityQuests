package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
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

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.MOB_KILL);
        for (QuestController controller : controllers) {
            updateQuest(controller, killer, 1, ObjectiveType.MOB_KILL, entity.getType().toString());
        }
    }
}
