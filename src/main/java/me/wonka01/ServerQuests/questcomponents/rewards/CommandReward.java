package me.wonka01.ServerQuests.questcomponents.rewards;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CommandReward implements Reward {
    private String command;

    public CommandReward(String command) {
        this.command = command;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        if (player.isOnline() && player.getName() != null) {
            String commandToRun = command.replaceAll("player", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToRun);
        }
    }
}
