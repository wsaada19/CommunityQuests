package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.events.questevents.MoneyQuest;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyQuestCommand implements SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Messages messages = LanguageConfig.getConfig().getMessages();
        if (!player.hasPermission(PermissionNode.MONEY)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoPermission()));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getInvalidCommand()));
        }

        try {
            double money = Double.parseDouble(args[1]);
            MoneyQuest moneyQuest = new MoneyQuest(ActiveQuests.getActiveQuestsInstance(), JavaPlugin.getPlugin(ServerQuests.class).getEconomy());
            boolean questFound = moneyQuest.tryAddItemsToQuest(money, player);

            if(!questFound) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoActiveDonateQuests()));
            }
        } catch(NumberFormatException exception) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Invalid number provided to "));
        }

    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
