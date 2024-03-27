package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestListener {
    protected ActiveQuests activeQuests;

    public QuestListener(ActiveQuests activeQuests) {
        this.activeQuests = activeQuests;
    }

    protected boolean updateQuest(QuestController controller, Player player, double amount, Objective obj) {
        if (!isEnabledInWorld(controller.getEventConstraints().getWorlds(), player.getWorld().getName())) {
            return false;
        }
        return controller.updateQuest(amount, player, obj);
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            String mobName) {
        controller.getQuestData().getObjectives().stream().forEach(objective -> {
            if (objective.isGoalComplete() == false && objective.getType().equals(type)
                    && (objective.getMaterials().isEmpty() || objective.getMobNames().contains(mobName))) {
                updateQuest(controller, player, amount, objective);
            }
        });
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            Material material) {
        controller.getQuestData().getObjectives().stream().forEach(objective -> {
            if (objective.isGoalComplete() == false && objective.getType().equals(type)
                    && (objective.getMaterials().isEmpty() || objective.getMaterials().contains(material))) {
                updateQuest(controller, player, amount, objective);
            }
        });
    }

    protected List<QuestController> tryGetControllersOfObjectiveType(ObjectiveType type) {
        List<QuestController> controllers = new ArrayList<>();
        for (QuestController controller : activeQuests.getActiveQuestsList()) {
            if (controller.getObjectiveTypes().contains(type)) {
                controllers.add(controller);
            }
        }
        return controllers;
    }

    private boolean isEnabledInWorld(List<String> worldList, String world) {
        return worldList.isEmpty() || worldList.contains(world);
    }
}
