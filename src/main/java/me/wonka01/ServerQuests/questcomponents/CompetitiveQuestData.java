package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import lombok.Getter;

public class CompetitiveQuestData extends QuestData {

    @Getter
    private BasePlayerComponent players;

    public CompetitiveQuestData(String displayName, String description,
            BasePlayerComponent players, String questType, int durationLeft, Material displayItem,
            String questId, String afterQuestCommand, String beforeQuestCommand, List<Objective> objectives) {
        super(displayName, description, questType, durationLeft, displayItem, questId,
                afterQuestCommand, beforeQuestCommand, objectives);
        this.players = players;
    }

    @Override
    public double getAmountCompleted() {
        PlayerData playerData = players.getTopPlayer();
        if (playerData != null) {
            return playerData.getAmountContributed();
        }
        return 0;
    }

    @Override
    public double getPercentageComplete() {
        PlayerData playerData = players.getTopPlayer();
        if (playerData != null) {
            return playerData.getAmountContributed() / this.getQuestGoal();
        }
        return 0;
    }

    @Override
    public boolean isGoalComplete() {
        PlayerData playerData = players.getTopPlayer();
        if (playerData == null) {
            return false;
        }
        return (getQuestGoal() > 0 && playerData.getAmountContributed() >= getQuestGoal());
    }

    // This really means is goal complete for the top player
    @Override
    public boolean isGoalComplete(Objective objective) {
        PlayerData playerData = players.getTopPlayer();
        if (playerData == null) {
            return false;
        }
        return (getQuestGoal() > 0 && playerData.getAmountContributed() >= getQuestGoal());
    }

    public boolean isGoalComplete(Objective objective, Player player, Integer objectiveId) {
        double amountContributed = players.getAmountContributedByObjectiveId(player, objectiveId);
        return (objective.getGoal() > 0 && amountContributed >= objective.getGoal());
    }

    @Override
    public EventType getEventType() {
        return EventType.COMPETITIVE;
    }
}
