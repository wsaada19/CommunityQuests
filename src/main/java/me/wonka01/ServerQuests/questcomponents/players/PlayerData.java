package me.wonka01.ServerQuests.questcomponents.players;

// Stores data for each player who has contributed to a quest.
public class PlayerData {

    private double amountContributed;
    private String playerName;

    public PlayerData(String name) {
        amountContributed = 0;
        playerName = name;
    }

    public PlayerData(String name, int amountContributed) {
        this.amountContributed = amountContributed;
        playerName = name;
    }

    public String getDisplayName() {
        return playerName;
    }

    public double getAmountContributed() {
        return amountContributed;
    }

    public void increaseContribution(double count) {
        amountContributed += count;
    }
}
