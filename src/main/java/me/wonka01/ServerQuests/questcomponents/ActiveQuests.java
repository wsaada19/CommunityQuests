package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

// Singleton class that stores all active quests running on the server
public class ActiveQuests {

    private static ActiveQuests activeQuestsInstance;
    private static int questLimit;

    private List<QuestController> activeQuestsList = new ArrayList<>();

    public ActiveQuests() {
        activeQuestsInstance = this;
    }

    public static void setQuestLimit(int limit) {
        questLimit = limit;
    }

    public static ActiveQuests getActiveQuestsInstance() {
        return activeQuestsInstance;
    }

    public void endAllQuests() {
        for (QuestController controller : activeQuestsList) {
            BarManager.closeBar(controller.getQuestId());
            if (controller != null) {
                controller.getQuestBar().removeBossBar();
            }
        }
        activeQuestsList.clear();
    }

    public void endQuest(UUID questId) {
        BarManager.closeBar(questId);
        QuestController controller = getQuestById(questId);
        if (controller != null) {
            controller.getQuestBar().removeBossBar();
            activeQuestsList.remove(controller);
        }
        if (activeQuestsList.size() > 0) {
            BarManager.startShowingPlayersBar(activeQuestsList.get(0).getQuestId());
        }
    }

    public boolean beginNewQuest(QuestModel questModel, EventType eventType) {
        if (activeQuestsList.size() >= questLimit)
            return false;

        QuestTypeHandler typeHandler = new QuestTypeHandler(eventType);
        QuestController controller = typeHandler.createQuestController(questModel);
        activeQuestsList.add(controller);
        if (questModel.getBeforeQuestCommand() != null && !questModel.getBeforeQuestCommand().isEmpty()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), questModel.getBeforeQuestCommand());
        }

        controller.broadcast("questStartMessage");
        BarManager.startShowingPlayersBar(controller.getQuestId());
        return true;
    }

    public void beginQuestFromSave(QuestController controller) {
        activeQuestsList.add(controller);
        BarManager.startShowingPlayersBar(controller.getQuestId());
    }

    public List<QuestController> getActiveQuestsList() {
        if (activeQuestsList == null) {
            activeQuestsList = new ArrayList<>();
        }
        return activeQuestsList;
    }

    public QuestController getQuestById(UUID questId) {
        for (QuestController controller : activeQuestsList) {
            if (controller.getQuestId().equals(questId)) {
                return controller;
            }
        }
        return null;
    }
}
