package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class EventsCommands implements CommandExecutor {

    private HashMap<String, SubCommand> subCommands;
    private final String commandPrefix = "sq";

    public void setup()
    {
        JavaPlugin.getPlugin(ServerQuests.class).getCommand(commandPrefix).setExecutor(this);
        subCommands = new HashMap<String, SubCommand>();
        subCommands.put("start", new StartCommand());
        subCommands.put("stop", new StopQuestCommand());
        subCommands.put("togglebar", new ToggleBarCommand());
        subCommands.put("view", new ViewQuestsCommand());
        subCommands.put("reload", new ReloadCommand());
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player)){return false;}
        Player player = (Player)commandSender;
        if(args.length < 1){
            player.sendMessage("&cPlease enter another command");
            return false;
        }
        String subCommandPrefix = args[0];

        if(!subCommands.containsKey(subCommandPrefix)){
            player.sendMessage("&cPlease enter another command");
            return false;
        }

        subCommands.get(subCommandPrefix).onCommand(player, args);
        return true;
    }
}
