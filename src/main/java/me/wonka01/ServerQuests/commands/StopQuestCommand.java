package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.StopEventGui;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StopQuestCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {

        if(!player.hasPermission("serverevents.stop")){
            player.sendMessage("You do not have permission to perform this action");
            return;
        }
        StopEventGui stopEventGui = JavaPlugin.getPlugin(ServerQuests.class).getStopGui();
        stopEventGui.initializeItems();
        stopEventGui.openInventory(player);
    }
}
