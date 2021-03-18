package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DonateQuestCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission("serverevents.view")) {
            player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return;
        }
        JavaPlugin.getPlugin(ServerQuests.class).getQuestsGui().openInventory(player);
    }
}
