package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.events.MoneyQuest;
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
        return PermissionNode.MONEY;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (args.length < 2) {

            String invalidCmd = getPlugin().getMessages().message("invalidCommand");
            player.sendMessage(invalidCmd);
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

            String noActiveDonateQuests = getPlugin().getMessages().message("noActiveDonateQuests");
            player.sendMessage(noActiveDonateQuests);
        } catch (NumberFormatException exception) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Invalid number provided to "));
        }

    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
