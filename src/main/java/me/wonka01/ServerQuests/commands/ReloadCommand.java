package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.RELOAD_QUEST;
    }

    public void onCommand(Player player, String[] args) {
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
