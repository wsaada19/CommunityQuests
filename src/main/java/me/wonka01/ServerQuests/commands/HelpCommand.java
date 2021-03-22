package me.wonka01.ServerQuests.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe Community Quests plugin allows servers to run quests that the entire server either works together or competes against one another to complete an objective. To view active quests use /cq view, to begin a new quests use /cq start. For donation quests, use the /cq donate command to open up the donation GUI."));
    }
}
