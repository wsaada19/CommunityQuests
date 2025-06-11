package me.wonka01.ServerQuests.questcomponents.rewards.types;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

public class CommandReward implements Reward {
    private String command;

    public CommandReward(String command) {
        this.command = command;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if (player.isOnline() && player.getName() != null) {
            String commandToRun = command.replaceAll("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToRun);
        }
    }

    @Override
    public String toString() {
        return "Command reward \n Command: " + command;
    }

    @Override
    public String getType() {
        return "command";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("command", command);
        return json;
    }
}
