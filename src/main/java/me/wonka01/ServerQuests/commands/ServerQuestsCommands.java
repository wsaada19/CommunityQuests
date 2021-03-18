package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ServerQuestsCommands implements CommandExecutor {

    private HashMap<String, SubCommand> subCommands;

    public void setup() {
        String commandPrefix = "cq";

        JavaPlugin.getPlugin(ServerQuests.class).getCommand(commandPrefix).setExecutor(this);
        subCommands = new HashMap<String, SubCommand>();
        subCommands.put("start", new StartCommand());
        subCommands.put("stop", new StopQuestCommand());
        subCommands.put("togglebar", new ToggleBarCommand());
        //subCommands.put("togglemessages", new ToggleMessageCommand());
        subCommands.put("view", new ViewQuestsCommand());
        subCommands.put("reload", new ReloadCommand());
        subCommands.put("donate", new DonateQuestCommand());
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "[ServerQuests] Invalid command");
            return false;
        }
        String subCommandPrefix = args[0];

        if (!subCommands.containsKey(subCommandPrefix)) {
            player.sendMessage(ChatColor.RED + "[ServerQuests] Invalid command");
            return false;
        }

        subCommands.get(subCommandPrefix).onCommand(player, args);
        return true;
    }
}
