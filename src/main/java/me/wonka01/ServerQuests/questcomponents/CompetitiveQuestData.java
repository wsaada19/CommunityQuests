package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.questcomponents.players.PlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

public class CompetitiveQuestData extends QuestData {

    private PlayerComponent playersReference;

    public CompetitiveQuestData(int start, String displayName, String description,
                                PlayerComponent playersReference, String questType, int amountComplete) {
        super(start, displayName, description, questType, amountComplete);
        this.playersReference = playersReference;
    }

    @Override
    public int getAmountCompleted() {
        PlayerData playerData = playersReference.getTopPlayerData();
        if (playerData == null) {
            return 0;
        }

        return playerData.getAmountContributed();
    }

    @Override
    public boolean isQuestComplete() {
        PlayerData playerData = playersReference.getTopPlayerData();
        if (playerData == null) {
            return false;
        }
        return (playerData.getAmountContributed() >= getQuestGoal());

    }
}
