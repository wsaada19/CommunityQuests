package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ReloadCommand extends SubCommand {
    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission(PermissionConstants.RELOAD_QUEST)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
            return;
        }
        reload(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        reload(sender);
    }

    private void reload(CommandSender sender) {
        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);

        plugin.reloadConfiguration();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getReloadCommand()));
    }
}
