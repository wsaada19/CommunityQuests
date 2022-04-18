package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ToggleBarCommand implements SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (player.hasPermission(PermissionNode.HIDE_BAR)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
            return;
        }
        BarManager.toggleShowPlayerBar(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
