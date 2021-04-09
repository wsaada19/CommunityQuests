package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class DonateQuestCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        Messages messages = LanguageConfig.getConfig().getMessages();
        if (!player.hasPermission(PermissionConstants.VIEW_QUEST)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoPermission()));
            return;
        }

        List<QuestController> controllerList = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        for (QuestController controller : controllerList) {
            if (controller.getObjectiveType() == ObjectiveType.GUI) {
                JavaPlugin.getPlugin(ServerQuests.class).getQuestsGui().openInventory(player);
                return;
            }
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getNoActiveDonateQuests()));
    }
}
