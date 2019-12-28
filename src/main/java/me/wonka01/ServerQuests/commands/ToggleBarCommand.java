package me.wonka01.ServerQuests.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ToggleBarCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        player.sendMessage(ChatColor.RED + "Invalid command");

    }
}
