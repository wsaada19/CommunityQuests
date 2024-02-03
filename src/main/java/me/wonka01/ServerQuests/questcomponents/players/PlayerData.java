package me.wonka01.ServerQuests.questcomponents.players;

import java.util.UUID;

import lombok.Getter;

@Getter
public class PlayerData {

    private double amountContributed;
    private String name;
    private UUID uuid;

    public PlayerData(String name, UUID uuid) {
        amountContributed = 0;
        this.name = name;
        this.uuid = uuid;
    }

    public PlayerData(String name, int amountContributed, UUID uuid) {
        this.amountContributed = amountContributed;
        this.name = name;
        this.uuid = uuid;
    }

    public void increaseContribution(double count) {
        amountContributed += count;
    }
}
