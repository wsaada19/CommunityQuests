package me.wonka01.ServerQuests.util;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.*;
import me.wonka01.ServerQuests.questcomponents.players.PlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import java.util.Map;
import java.util.UUID;

public class EventTypeHandler {
    private EventType eventType;

    public EventTypeHandler(EventType eventType) {
        this.eventType = eventType;
    }

    public EventTypeHandler(String type) {
        if (type.equalsIgnoreCase("comp")) {
            eventType = EventType.COMPETITIVE;
        } else {
            eventType = EventType.COLLAB;
        }
    }

    public QuestController createQuestController(QuestModel questModel) {

        QuestBar bar = new QuestBar(questModel.getDisplayName());
        PlayerComponent playerComponent = new PlayerComponent(questModel.getRewards());
        QuestData data = getQuestData(questModel, 0, playerComponent);
        QuestConstraints questConstraints = new QuestConstraints(questModel.getItemNames(), questModel.getMobNames());

        return new QuestController(data, bar, playerComponent, questConstraints, questModel.getObjective());
    }

    public QuestController createControllerFromSave(QuestModel questModel, Map<UUID, PlayerData> players,
                                                    int amountComplete) {

        PlayerComponent playerComponent = new PlayerComponent(questModel.getRewards(), players);
        QuestData data = getQuestData(questModel, amountComplete, playerComponent);

        QuestBar bar = new QuestBar(questModel.getDisplayName());
        bar.updateBarProgress(((double) amountComplete / (double) questModel.getQuestGoal()));
        QuestConstraints questConstraints = new QuestConstraints(questModel.getItemNames(), questModel.getMobNames());

        return new QuestController(data, bar, playerComponent, questConstraints, questModel.getObjective());
    }

    private QuestData getQuestData(QuestModel questModel, int amountComplete, PlayerComponent playerComponent) {

        if (eventType == EventType.COMPETITIVE) {
            return new CompetitiveQuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), playerComponent, questModel.getQuestId(), amountComplete);
        } else {
            return new QuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), questModel.getQuestId(), amountComplete);
        }
    }
}
