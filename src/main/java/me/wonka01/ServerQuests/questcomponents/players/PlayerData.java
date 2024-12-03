package me.wonka01.ServerQuests.questcomponents.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerData {

    @Setter
    private String name;
    private UUID uuid;
    Map<Integer, Double> objectiveContributions;
    long lastUpdated;

    public PlayerData(String name, UUID uuid) {
        objectiveContributions = new HashMap<>();
        this.name = name;
        this.uuid = uuid;
        this.lastUpdated = System.currentTimeMillis();
    }

    public PlayerData(String name, UUID uuid, Map<Integer, Double> objectives, long lastUpdated) {
        this.objectiveContributions = objectives;
        this.name = name;
        this.uuid = uuid;
        this.lastUpdated = lastUpdated;
    }

    public double getAmountContributed() {
        double total = 0.0;
        for (Map.Entry<Integer, Double> entry : objectiveContributions.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    public double getAmountContributedByObjectiveId(int objectiveId) {
        if (!objectiveContributions.containsKey(objectiveId)) {
            return 0.0;
        }
        return objectiveContributions.get(objectiveId);
    }

    public void increaseContribution(double count, int objectiveId) {
        if (!objectiveContributions.containsKey(objectiveId)) {
            objectiveContributions.put(objectiveId, 0.0);
        }
        objectiveContributions.put(objectiveId, objectiveContributions.get(objectiveId) + count);
        lastUpdated = System.currentTimeMillis();
    }
}
