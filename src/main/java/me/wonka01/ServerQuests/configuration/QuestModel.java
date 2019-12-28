package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.rewards.Reward;

import java.util.ArrayList;
import java.util.List;

public class QuestModel {

    private String questId;
    private String displayName;
    private String eventDescription;
    private int secondsToComplete;
    private int questGoal;
    private EventListenerHandler.EventListenerType eventType;
    private List<String> mobNames;
    private ArrayList<Reward> rewards;
    private List<String> blockNames;

    public QuestModel(String questId, String displayName, String eventDescription,
                      int secondsToComplete, int questGoal, EventListenerHandler.EventListenerType eventType,
                      List<String> mobNames, ArrayList<Reward> rewards, List<String> blockNames)
    {
        this.questId = questId;
        this.displayName = displayName;
        this.eventDescription = eventDescription;
        this.secondsToComplete = secondsToComplete;
        this.questGoal = questGoal;
        this.eventType = eventType;
        this.mobNames = mobNames;
        this.rewards = rewards;
        this.blockNames = blockNames;
    }

    public String getQuestId() {
        return questId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getSecondsToComplete() {
        return secondsToComplete;
    }

    public int getQuestGoal() {
        return questGoal;
    }

    public EventListenerHandler.EventListenerType getEventType(){ return eventType; }
    public List<String> getMobNames(){return mobNames;}
    public ArrayList<Reward> getRewards() {return rewards;}

    public List<String> getBlockNames() {return blockNames;}


}
