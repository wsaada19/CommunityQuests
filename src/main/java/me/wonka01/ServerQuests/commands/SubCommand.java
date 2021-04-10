package me.wonka01.ServerQuests.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public SubCommand() {
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract void onCommand(CommandSender player, String[] args);
}
