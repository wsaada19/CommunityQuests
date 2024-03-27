package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class KillPlayerEvent extends QuestListener implements Listener {

    public KillPlayerEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onKillPlayer(PlayerDeathEvent event) {

        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.PLAYER_KILL);
        for (QuestController controller : controllers) {
            controller.getQuestData().getObjectives().stream()
                    .filter(objective -> objective.isGoalComplete() == false
                            && objective.getType().equals(ObjectiveType.PLAYER_KILL))
                    .forEach(objective -> updateQuest(controller, killer, 1, objective));
        }
    }
}
