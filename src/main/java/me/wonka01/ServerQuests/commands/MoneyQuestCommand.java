package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
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

import java.util.List;

public class MoneyQuestCommand extends SubCommand {
    private final MoneyQuest eventHandler;

    public MoneyQuestCommand(ServerQuests plugin, MoneyQuest eventHandler) {
        super(plugin);
        this.eventHandler = eventHandler;
    }

    @Override
    public @NonNull String getPermission() {
        return null;
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
        } catch (NumberFormatException exception) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Invalid number provided to "));
        }

    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
