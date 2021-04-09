package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getHelpMessage()));
    }
}
