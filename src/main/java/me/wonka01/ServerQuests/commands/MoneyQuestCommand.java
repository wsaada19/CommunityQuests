package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.events.questevents.MoneyQuest;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MoneyQuestCommand implements SubCommand {
    private MoneyQuest eventHandler;

    public MoneyQuestCommand(MoneyQuest eventHandler) {
        super();
        this.eventHandler = eventHandler;
    }

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
            List<QuestController> controllerList = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            for (QuestController controller : controllerList) {
                if (controller.getObjectiveType() == ObjectiveType.GIVE_MONEY) {
                    eventHandler.tryAddItemsToQuest(money, player);
                    return;
                }
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoActiveDonateQuests()));
        } catch(NumberFormatException exception) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Invalid number provided to "));
        }

    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
