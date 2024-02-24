package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.enums.EventType;
import org.bukkit.Material;

@Getter
public class QuestData {

    private final @NonNull String questId;
    private final String questType;
    private final int questGoal;
    private final String displayName;
    private final String description;
    private final Material displayItem;
    private final @NonNull EventType eventType = EventType.COLLAB;
    private double amountCompleted;
    private int questDuration;
    private String afterQuestCommand;
    private String beforeQuestCommand;

    public QuestData(int questGoal, String displayName, String description, String questType, int amountCompleted,
            int questDuration, Material displayItem, @NonNull String questId, String afterQuestCommand,
            String beforeQuestCommand) {
        this.questGoal = questGoal;
        this.questDuration = questDuration;
        this.amountCompleted = amountCompleted;
        this.displayName = displayName;
        this.description = description == null ? "" : description;
        this.questId = questId;
        this.questType = questType;
        this.displayItem = displayItem;
        this.afterQuestCommand = afterQuestCommand;
        this.beforeQuestCommand = beforeQuestCommand;
    }

    public double getPercentageComplete() {
        return amountCompleted / questGoal;
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
}
