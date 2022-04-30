package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommunityQuestsCommands implements CommandExecutor {

    private ServerQuests plugin;

    private HashMap<String, SubCommand> subCommands;

    public void setup(ServerQuests plugin) {

        this.plugin = plugin;

        plugin.getCommand("communityquests").setExecutor(this);
        subCommands = new HashMap<>();
        subCommands.put("start", new StartCommand());
        subCommands.put("stop", new StopQuestCommand());
        subCommands.put("togglebar", new ToggleBarCommand());
        subCommands.put("view", new ViewQuestsCommand(plugin));
        subCommands.put("reload", new ReloadCommand());
        subCommands.put("donate", new DonateQuestCommand());
        subCommands.put("help", new HelpCommand());
        /*subCommands.put("money", new MoneyQuestCommand(new MoneyQuest(ActiveQuests.getActiveQuestsInstance(), ServerQuests.economy)));*/
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {

            SubCommand cmd = getSubCommand(args[1]);

            if (!sender.hasPermission(cmd.getPermission())) {

                String noPermMsg = plugin.getMessages().message("noPermission");
                sender.sendMessage(noPermMsg);
                return true;
            }

            if (sender instanceof Player) {

                cmd.onCommand((Player) sender, args);
            } else
                cmd.onCommand(sender, args);


        } catch (IndexOutOfBoundsException ignored) {

            String invalidMessage = plugin.getMessages().message("invalidCommand");
            sender.sendMessage(invalidMessage);
        }

        return true;
    }

    private @NonNull SubCommand getSubCommand(@NonNull String arg) {

        for (String name : subCommands.keySet())
            if (name.equalsIgnoreCase(arg))
                return subCommands.get(name);

        return getSubCommand("help");
    }
}
