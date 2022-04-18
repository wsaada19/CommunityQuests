package me.wonka01.ServerQuests.configuration;

import lombok.Getter;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.rewards.Reward;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestModel {

    private String questId;
    private String displayName;
    private String eventDescription;
    private int durationInSeconds;
    private int questGoal;
    private ObjectiveType objective;
    private List<String> mobNames;
    private ArrayList<Reward> rewards;
    private List<String> itemNames;

    public QuestModel(String questId, String displayName, String eventDescription,
                      int durationInSeconds, int questGoal, ObjectiveType objective,
                      List<String> mobNames, ArrayList<Reward> rewards, List<String> itemNames) {
        this.questId = questId;
        this.displayName = displayName;
        this.eventDescription = eventDescription;
        this.durationInSeconds = durationInSeconds;
        this.questGoal = questGoal;
        this.objective = objective;
        this.mobNames = mobNames;
        this.rewards = rewards;
        this.itemNames = itemNames;
    }
}
