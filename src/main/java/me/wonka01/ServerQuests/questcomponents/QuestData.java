package me.wonka01.ServerQuests.questcomponents;

public class QuestData {

    private String questType;
    private int questGoal;
    private double amountCompleted;
    private int questDuration;

    private String displayName;
    private String description;

    public QuestData(int questGoal, String displayName, String description, String questType, int amountCompleted, int questDuration) {
        this.questGoal = questGoal;
        this.questDuration = questDuration;
        this.amountCompleted = amountCompleted;
        this.displayName = displayName;
        this.description = description;
        this.questType = questType;
    }

    public double getAmountCompleted() {
        return amountCompleted;
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

    public boolean hasGoal() {return questGoal > 0;}

    public int getQuestGoal() {
        return questGoal;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getQuestType() {
        return questType;
    }

    public int getQuestDuration() {
        return questDuration;
    }

}
