package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ToggleBarCommand extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (player.hasPermission(PermissionConstants.HIDE_BAR)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
            return;
        }
        BarManager.toggleShowPlayerBar(player);
    }
}
