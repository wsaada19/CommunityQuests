package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestListener {
    protected ActiveQuests activeQuests;

    public QuestListener(ActiveQuests activeQuests) {
        this.activeQuests = activeQuests;
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type) {
        QuestData questData = controller.getQuestData();
        List<Objective> objectives = questData.getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            if (objective.getType().equals(type)) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    protected boolean updateQuest(QuestController controller, Player player, double amount, Objective obj,
            int objectiveId) {
        if (!isEnabledInWorld(controller.getEventConstraints().getWorlds(), player.getWorld().getName())) {
            return false;
        }
        return controller.updateQuest(amount, player, obj, objectiveId);
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            String mobName) {
        QuestData questData = controller.getQuestData();
        List<Objective> objectives = questData.getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            if (objective.getType().equals(type)
                    && (objective.getMobNames().isEmpty() || objective.getMobNames().contains(mobName))) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            Material material) {
        QuestData questData = controller.getQuestData();
        List<Objective> objectives = questData.getObjectives();
        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            if (objective.getType().equals(type)
                    && (objective.getMaterials().isEmpty() || objective.getMaterials().contains(material))) {
                updateQuest(controller, player, amount, objective, i);
            }
        }
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
