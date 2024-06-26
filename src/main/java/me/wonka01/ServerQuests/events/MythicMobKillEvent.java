package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Bukkit;
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
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.MYTHIC_MOB);
        LivingEntity entity = event.getKiller();
        if (!(entity instanceof Player)) {
            return;
        }

        for (QuestController controller : controllers) {
            updateQuest(controller, (Player) event.getKiller(), 1, ObjectiveType.MYTHIC_MOB,
                    event.getMobType().getInternalName(), event.getMobType().getInternalName());
        }
    }
}
