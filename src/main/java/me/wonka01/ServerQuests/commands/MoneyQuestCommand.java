package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.events.MoneyQuest;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class MoneyQuestCommand extends SubCommand {

    public MoneyQuestCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.MONEY;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length < 2) {
            String invalidCmd = getPlugin().getMessages().message("invalidCommand");
            player.sendMessage(invalidCmd);
            return;
        }

        try {
            double money = Double.parseDouble(args[1]);
            MoneyQuest moneyQuest = new MoneyQuest(ActiveQuests.getActiveQuestsInstance(), JavaPlugin.getPlugin(ServerQuests.class).getEconomy());
            boolean questFound = moneyQuest.tryAddItemsToQuest(money, player);

            if(!questFound) {
                String noActiveDonateQuests = getPlugin().getMessages().message("noActiveDonateQuests");
                player.sendMessage(noActiveDonateQuests);
            }
        } catch (NumberFormatException exception) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Invalid number provided to "));
        }

    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
