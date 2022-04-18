package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class DonateQuestCommand implements SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        Messages messages = LanguageConfig.getConfig().getMessages();
        if (!player.hasPermission(PermissionNode.DONATE)) {
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

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
