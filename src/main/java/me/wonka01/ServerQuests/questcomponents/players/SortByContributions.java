package me.wonka01.ServerQuests.questcomponents.players;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

public class SortByContributions implements Comparator<UUID> {
    private Map<UUID, PlayerData> map;

    public SortByContributions(Map<UUID, PlayerData> playerComponent) {
        this.map = playerComponent;
    }

    public int compare(UUID p1Id, UUID p2Id) {
        int contributionComparison = Double.compare(map.get(p2Id).getAmountContributed(),
                map.get(p1Id).getAmountContributed());

        if (contributionComparison == 0) {
            // if same amount whoever updated first would be higher
            return Long.compare(map.get(p1Id).getLastUpdated(), map.get(p2Id).getLastUpdated());
        }
        return contributionComparison;
    }
}
