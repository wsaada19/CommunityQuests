package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

public class CompetitiveQuestData extends QuestData {

    private BasePlayerComponent players;

    public CompetitiveQuestData(int start, String displayName, String description,
                                BasePlayerComponent players, String questType, int amountComplete, int durationLeft) {
        super(start, displayName, description, questType, amountComplete, durationLeft);
        this.players = players;
    }

    @Override
    public double getAmountCompleted() {
        PlayerData playerData = players.getTopPlayerData();
        if (playerData != null) {
            return playerData.getAmountContributed();
        }
        return 0;
    }

    @Override
    public double getPercentageComplete() {
        PlayerData playerData = players.getTopPlayerData();
        if (playerData != null) {
            return playerData.getAmountContributed() / this.getQuestGoal();
        }
        return 0;
    }

    @Override
    public boolean isGoalComplete() {
        PlayerData playerData = players.getTopPlayerData();
        if (playerData == null) {
            return false;
        }
        return (getQuestGoal() > 0 && playerData.getAmountContributed() >= getQuestGoal());

    }
}
