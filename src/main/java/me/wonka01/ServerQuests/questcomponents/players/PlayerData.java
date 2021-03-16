package me.wonka01.ServerQuests.questcomponents.players;

// Stores data for each player who has contributed to a quest.
public class PlayerData implements Comparable<PlayerData> {

    private int amountContributed;
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

    public int getAmountContributed() {
        return amountContributed;
    }

    public void increaseContribution(int count) {
        amountContributed += count;
    }

    // override equals and hashCode
    public int compareTo(PlayerData playerData) {
        return (this.amountContributed - playerData.getAmountContributed());
    }

}
