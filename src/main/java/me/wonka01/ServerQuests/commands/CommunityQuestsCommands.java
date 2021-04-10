package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

public class CommunityQuestsCommands implements CommandExecutor {

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
        subCommands.put("help", new HelpCommand());
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            try {
                if (args.length < 1 || !subCommands.containsKey(args[0])) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidCommand()));
                    return false;
                } else {
                    subCommands.get(args[0]).onCommand(commandSender, args);
                    return true;
                }
            } catch(NotImplementedException ex) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getNoPermission()));
                return false;
            }
        }
        Player player = (Player) commandSender;

        if (args.length < 1 || !subCommands.containsKey(args[0])) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getInvalidCommand()));
            return false;
        }

        subCommands.get(args[0]).onCommand(player, args);
        return true;
    }
}
