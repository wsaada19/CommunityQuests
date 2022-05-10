package me.wonka01.ServerQuests.questcomponents.players;

import lombok.Getter;

@Getter
public class PlayerData {

    private double amountContributed;
    private String name;

    public PlayerData(String name) {
        amountContributed = 0;
        this.name = name;
    }

    public PlayerData(String name, int amountContributed) {
        this.amountContributed = amountContributed;
        this.name = name;
    }

    public void increaseContribution(double count) {
        amountContributed += count;
    }
}
