package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand extends SubCommand {
    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission(PermissionConstants.RELOAD_QUEST)) {
            return;
        }

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);

        plugin.reloadConfiguration();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getReloadCommand()));
    }
}
