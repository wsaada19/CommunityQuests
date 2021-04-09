package me.wonka01.ServerQuests.questcomponents.players;

import java.util.Comparator;
import java.util.UUID;

public class SortByContributions implements Comparator<UUID>
{
    private BasePlayerComponent playerComponent;

    public SortByContributions(BasePlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
    }

    public int compare(UUID p1Id, UUID p2Id) {
        return playerComponent.playerMap.get(p1Id).getAmountContributed() - playerComponent.playerMap.get(p2Id).getAmountContributed();
    }
}