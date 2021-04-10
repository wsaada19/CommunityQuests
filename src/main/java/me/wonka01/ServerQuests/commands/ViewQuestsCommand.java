package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.gui.ViewGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ViewQuestsCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Messages messages = LanguageConfig.getConfig().getMessages();
        if (!player.hasPermission(PermissionConstants.VIEW_QUEST)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoPermission()));
            return;
        }
        if (ActiveQuests.getActiveQuestsInstance().getActiveQuestsList().size() < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoActiveQuests()));
            return;
        }
        ViewGui view = JavaPlugin.getPlugin(ServerQuests.class).getViewGui();
        view.initializeItems(player);
        view.openInventory(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
