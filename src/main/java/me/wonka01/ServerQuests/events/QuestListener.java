package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
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

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type) {
        if (!isEnabledInWorld(controller.getEventConstraints().getWorlds(), player.getWorld().getName())) {
            return;
        }
        controller.updateQuest(amount, player, type);
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            String mobName) {
        controller.getQuestData().getObjectives().stream()
                .filter(objective -> objective.getType() == type)
                .filter(objective -> objective.getMobNames().isEmpty()
                        || Utils.contains(objective.getMobNames(), mobName))
                .forEach(objective -> updateQuest(controller, player, amount, type));
    }

    protected void updateQuest(QuestController controller, Player player, double amount, ObjectiveType type,
            Material material) {
        controller.getQuestData().getObjectives().stream()
                .filter(objective -> objective.getType() == type)
                .filter(objective -> objective.getMaterials().isEmpty() || objective.getMaterials().contains(material))
                .forEach(objective -> updateQuest(controller, player, amount, type));
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
