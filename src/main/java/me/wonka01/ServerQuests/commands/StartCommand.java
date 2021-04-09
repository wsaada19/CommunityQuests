package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StartCommand extends SubCommand {

    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission(PermissionConstants.START_QUEST)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to perform this action"));
            return;
        }

        if (args.length < 2) {
            //JavaPlugin.getPlugin(ServerQuests.class).getStartGui().initializeItems();
            JavaPlugin.getPlugin(ServerQuests.class).getStartGui().openInventory(player);
            return;
        }

        String questId = args[1];
        QuestModel questModel = JavaPlugin.getPlugin(ServerQuests.class).questLibrary.getQuestModelById(questId);
        if (questModel == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe quest name you entered does not exist"));
            return;
        }

        if (args.length < 3) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\"You must enter a quest type; coop or comp\""));
            return;
        }
        EventType eventType;

        if (args[2].equalsIgnoreCase("coop")) {
            eventType = EventType.COLLAB;
        } else if (args[2].equalsIgnoreCase("comp")) {
            eventType = EventType.COMPETITIVE;
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\"You must enter a quest type; coop or comp\""));
            return;
        }

        boolean questCreated = ActiveQuests.getActiveQuestsInstance().beginNewQuest(questModel, eventType);
        if (!questCreated) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe quest could not be created, the number of active quests has reached its limit"));
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aQuest started!"));

    }

    public void onCommand(CommandSender sender, String[] args) {

        if (args.length < 2) {
            return;
        }

        String questId = args[1];
        QuestModel questModel = JavaPlugin.getPlugin(ServerQuests.class).questLibrary.getQuestModelById(questId);
        if (questModel == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe quest name you entered does not exist"));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\"You must enter a quest type; coop or comp\""));
            return;
        }
        EventType eventType;

        if (args[2].equalsIgnoreCase("coop")) {
            eventType = EventType.COLLAB;
        } else if (args[2].equalsIgnoreCase("comp")) {
            eventType = EventType.COMPETITIVE;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c\"You must enter a quest type; coop or comp\""));
            return;
        }

        boolean questCreated = ActiveQuests.getActiveQuestsInstance().beginNewQuest(questModel, eventType);
        if (!questCreated) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe quest could not be created, the number of active quests has reached its limit"));
        }
    }
}
