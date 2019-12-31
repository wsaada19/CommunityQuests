package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.gui.ViewGui;
import org.bukkit.entity.Player;

public class ViewQuestsCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        if(!player.hasPermission("serverevents.view")){
            player.sendMessage("You do not have permission for this command.");
            return;
        }
        ViewGui view = new ViewGui(player);
        view.openInventory();
    }
}
