package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;
import me.wonka01.ServerQuests.enums.EventType;
import org.bukkit.Material;

@Getter
public class QuestData {

    private String questId;
    private int questGoal;
    private double amountCompleted;
    private int questDuration;
    private String displayName;
    private String description;
    private Material displayItem;

    public QuestData(int questGoal, String displayName, String description, String questType, int amountCompleted, int questDuration, Material displayItem, String questId) {
        this.questGoal = questGoal;
        this.questDuration = questDuration;
        this.amountCompleted = amountCompleted;
        this.displayName = displayName;
        this.description = description;
        this.questId = questType;
        this.displayItem = displayItem;
    }

    public double getPercentageComplete() {
        return (amountCompleted / (double) questGoal);
    }

    public void addToQuestProgress(double amountToIncrease) {
        amountCompleted += amountToIncrease;
    }

    public void decreaseDuration(int amountToDecrease) {
        questDuration -= amountToDecrease;
    }

    // Always false if no goal is set and the quest is using a timer...
    public boolean isGoalComplete() {
        return (hasGoal() && amountCompleted >= questGoal);
    }

    public boolean hasGoal() {
        return questGoal > 0;
    }

    public EventType getEventType() {
        return EventType.COLLAB;
    }
}
