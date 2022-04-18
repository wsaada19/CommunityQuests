package me.wonka01.ServerQuests.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Class didn't hold any variables so interface is the better choice
public interface SubCommand {

    void onCommand(Player player, String[] args);

    void onCommand(CommandSender player, String[] args);
}
