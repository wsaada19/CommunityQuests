package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.gui.StopGui;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class StopQuestCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission(PermissionNode.STOP_QUEST)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
            return;
        }
        StopGui stopGui = JavaPlugin.getPlugin(ServerQuests.class).getStopGui();
        stopGui.initializeItems();
        stopGui.openInventory(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
