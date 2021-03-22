package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.gui.ViewGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ViewQuestsCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission(PermissionConstants.VIEW_QUEST)) {
            player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return;
        }
        if (ActiveQuests.getActiveQuestsInstance().getActiveQuestsList().size() < 1) {
            player.sendMessage(ChatColor.RED + "There are currently no active quests");
            return;
        }
        ViewGui view = new ViewGui(player);
        view.openInventory();
    }
}
