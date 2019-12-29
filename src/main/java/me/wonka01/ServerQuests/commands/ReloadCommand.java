package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand extends SubCommand {
    public void onCommand(Player player, String[] args) {

        if(!player.hasPermission("serverevents.reload")){return;}

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);

        plugin.loadQuestLibraryFromConfig();
        plugin.loadStartEventGui();

    }
}
