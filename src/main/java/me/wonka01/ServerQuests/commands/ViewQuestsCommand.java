package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.gui.ViewEventsGui;
import org.bukkit.entity.Player;

public class ViewQuestsCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        ViewEventsGui view = new ViewEventsGui(player);
        view.openInventory();
    }
}
