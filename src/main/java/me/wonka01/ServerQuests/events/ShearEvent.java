package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.List;

public class ShearEvent extends QuestListener implements Listener {

    public ShearEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onSheer(PlayerShearEntityEvent shearEvent) {
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.SHEAR);
        for (QuestController controller : controllers) {
            controller.getQuestData().getObjectives().stream()
                    .filter(objective -> objective.isGoalComplete() == false
                            && objective.getType().equals(ObjectiveType.SHEAR))
                    .forEach(objective -> updateQuest(controller, shearEvent.getPlayer(), 1, objective));
        }
    }
}
