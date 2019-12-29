package me.wonka01.ServerQuests.handlers;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.questcomponents.*;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import java.util.Map;
import java.util.UUID;

public class EventTypeHandler {
    public enum EventType {
        COLLAB,
        COMPETITIVE
    }

    private EventType eventType;

    public EventTypeHandler(EventType eventType) {
        this.eventType = eventType;
    }
    public EventTypeHandler(String type){
        if(type.equalsIgnoreCase("comp")){
            eventType = EventType.COMPETITIVE;
        } else {
            eventType = EventType.COLLAB;
        }
    }

    public QuestController createQuestController(QuestModel questModel) {
        switch (eventType){
            case COLLAB:
                return createCollaborativeQuestController(questModel);
            case COMPETITIVE:
                return createCompetitiveQuestController(questModel);
        }
        return null;
    }

    private QuestController createCollaborativeQuestController(QuestModel questModel) {
        QuestBar bar = new QuestBar(questModel.getDisplayName());
        BasePlayerComponent playerComponent = new BasePlayerComponent(questModel.getRewards());
        QuestData data = new QuestData(questModel.getQuestGoal(), questModel.getDisplayName(), questModel.getEventDescription(),
                questModel.getQuestId(), 0);
        EventConstraints eventConstraints = new EventConstraints(questModel.getBlockNames(), questModel.getMobNames());

        QuestController controller = new QuestController(data, bar, playerComponent, eventConstraints,
                questModel.getEventType());
        return controller;
    }

    public QuestController crateControllerFromSave(QuestModel questModel, Map<UUID, PlayerData> players,
                                                    int amountComplete){
        QuestData data;
        BasePlayerComponent playerComponent = new BasePlayerComponent(questModel.getRewards(), players);

        if(eventType == EventType.COMPETITIVE){
            data = new CompetitiveQuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), playerComponent, questModel.getQuestId(), amountComplete);
        } else {
            data = new QuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                    questModel.getEventDescription(), questModel.getQuestId(), amountComplete);
        }
        QuestBar bar = new QuestBar(questModel.getDisplayName());
        bar.updateBarProgress((double)amountComplete/(double)questModel.getQuestGoal());
        EventConstraints eventConstraints = new EventConstraints(questModel.getBlockNames(), questModel.getMobNames());

        QuestController controller = new QuestController(data, bar, playerComponent, eventConstraints, questModel.getEventType());
        return controller;
    }

    private QuestController createCompetitiveQuestController(QuestModel questModel)
    {
        QuestBar bar = new QuestBar(questModel.getDisplayName());
        BasePlayerComponent playerComponent = new BasePlayerComponent(questModel.getRewards());
        QuestData data = new CompetitiveQuestData(questModel.getQuestGoal(), questModel.getDisplayName(), questModel.getEventDescription(),
                playerComponent, questModel.getQuestId(), 0);
        EventConstraints eventConstraints = new EventConstraints(questModel.getBlockNames(), questModel.getMobNames());

        QuestController controller = new QuestController(data, bar, playerComponent, eventConstraints, questModel.getEventType());
        return controller;
    }

}
