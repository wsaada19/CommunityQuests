package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestListener {
    protected ActiveQuests activeQuests;
    private List<QuestController> controllersToRemove;

    public QuestListener(ActiveQuests activeQuests) {
        this.activeQuests = activeQuests;
        controllersToRemove = new ArrayList<QuestController>();
    }

    protected void updateQuest(QuestController controller, Player player, int amount) {
        boolean isQuestComplete = controller.updateQuest(amount, player);
        if (isQuestComplete) {
            controllersToRemove.add(controller);
        }
    }

    protected void removedFinishedQuests() {
        for (QuestController controller : controllersToRemove) {
            controller.handleQuestComplete();
        }
        controllersToRemove.clear();
    }
}
