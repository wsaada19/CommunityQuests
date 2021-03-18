package me.wonka01.ServerQuests.util;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.*;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
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
        BasePlayerComponent playerComponent = new BasePlayerComponent(questModel.getRewards());
        QuestData data = getQuestData(questModel, 0, playerComponent);
        EventConstraints eventConstraints = new EventConstraints(questModel.getItemNames(), questModel.getMobNames());

        return new QuestController(data, bar, playerComponent, eventConstraints, questModel.getObjective());
    }

    public QuestController createControllerFromSave(QuestModel questModel, Map<UUID, PlayerData> players,
                                                    int amountComplete) {

        BasePlayerComponent playerComponent = new BasePlayerComponent(questModel.getRewards(), players);
        QuestData data = getQuestData(questModel, amountComplete, playerComponent);

        QuestBar bar = new QuestBar(questModel.getDisplayName());
        bar.updateBarProgress((double) 1 - (amountComplete / (double) questModel.getQuestGoal()));
        EventConstraints eventConstraints = new EventConstraints(questModel.getItemNames(), questModel.getMobNames());

        return new QuestController(data, bar, playerComponent, eventConstraints, questModel.getObjective());
    }

    private QuestData getQuestData(QuestModel questModel, int amountComplete, BasePlayerComponent playerComponent) {

        if (eventType == EventType.COMPETITIVE) {
            return new CompetitiveQuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), playerComponent, questModel.getQuestId(), amountComplete);
        } else {
            return new QuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), questModel.getQuestId(), amountComplete);
        }
    }
}
