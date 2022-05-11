package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.Material;

public class CompetitiveQuestData extends QuestData {

    private BasePlayerComponent players;

    public CompetitiveQuestData(int start, String displayName, String description,
                                BasePlayerComponent players, String questType, int amountComplete, int durationLeft, Material displayItem, String questId) {
        super(start, displayName, description, questType, amountComplete, durationLeft, displayItem, questId);
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

    @Override
    public EventType getEventType() {
        return EventType.COMPETITIVE;
    }
}
