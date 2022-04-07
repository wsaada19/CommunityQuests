package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestListener {
    protected ActiveQuests activeQuests;

    public QuestListener(ActiveQuests activeQuests) {
        this.activeQuests = activeQuests;
    }

    protected void updateQuest(QuestController controller, Player player, int amount) {
        controller.updateQuest(amount, player);
        if (controller.getQuestData().isGoalComplete()) {
            controller.endQuest();
        }
    }

    protected List<QuestController> tryGetControllersOfEventType(ObjectiveType type) {
        List<QuestController> controllers = new ArrayList<QuestController>();

        for (QuestController controller : activeQuests.getActiveQuestsList()) {
            if (controller.getObjectiveType() == type) {
                controllers.add(controller);
            }
        }
        return controllers;
    }
}
