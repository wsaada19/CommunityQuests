package me.wonka01.ServerQuests.questcomponents;

public class QuestData {

    private String questType;
    private int questGoal;
    private int amountCompleted;

    private String displayName;
    private String description;

    public QuestData(int start, String displayName, String description, String questType, int amountCompleted) {
        questGoal = start;
        this.amountCompleted = amountCompleted;
        this.displayName = displayName;
        this.description = description;
        this.questType = questType;
    }

    public int getAmountCompleted() {
        return amountCompleted;
    }

    public double getPercentageComplete() {
        return ((double) amountCompleted / (double) questGoal);
    }

    public void addToQuestProgress(int amountToIncrease) {
        amountCompleted += amountToIncrease;
    }

    public boolean isQuestComplete() {
        return (amountCompleted >= questGoal);
    }

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
}
