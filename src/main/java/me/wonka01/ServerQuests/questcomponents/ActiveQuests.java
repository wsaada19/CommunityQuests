package me.wonka01.ServerQuests.questcomponents;

import com.sun.istack.internal.Nullable;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.util.EventTypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Singleton class that stores all active quests running on the server
public class ActiveQuests {

    private static ActiveQuests activeQuestsInstance;
    private static int questLimit;

    private List<QuestController> activeQuestsList;

    public ActiveQuests() {
        activeQuestsList = new ArrayList<QuestController>();
        activeQuestsInstance = this;
    }

    public static void setQuestLimit(int limit) {
        questLimit = limit;
    }

    public static ActiveQuests getActiveQuestsInstance() {
        return activeQuestsInstance;
    }

    // TODO Test this change
    public void endQuest(UUID questId) {
        activeQuestsList.remove(getQuestById(questId));
    }

    public boolean InitializeQuestListener(QuestModel questModel, EventType eventType) {
        if (activeQuestsList.size() >= questLimit) {
            return false;
        } else {
            EventTypeHandler typeHandler = new EventTypeHandler(eventType);
            QuestController controller = typeHandler.createQuestController(questModel);

            activeQuestsList.add(controller);
            BarManager.startShowingPlayersBar(controller.getQuestId()); // doesn't show if there are two active quests
            return true;
        }
    }

    public void startQuestWithController(QuestController controller) {
        activeQuestsList.add(controller);
        BarManager.startShowingPlayersBar(controller.getQuestId()); // doesn't show if there are two active quests
    }

    public List<QuestController> getActiveQuestsList() {
        if (activeQuestsList == null) {
            activeQuestsList = new ArrayList<QuestController>();
        }
        return activeQuestsList;
    }

    @Nullable
    public QuestController getQuestById(UUID questId) {
        for (QuestController controller : activeQuestsList) {
            if (controller.getQuestId().equals(questId)) {
                return controller;
            }
        }
        return null;
    }

    public void endAllQuests() {
        activeQuestsList.clear();
    }
}
