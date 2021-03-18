package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StartCommand extends SubCommand {

    public void onCommand(Player player, String[] args) {

        if (!player.hasPermission("serverevents.start")) {
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

        boolean questCreated = ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(questModel, eventType);
        if (!questCreated) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe quest could not be created, the number of active quests has reached its limit"));
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aQuest started!"));

    }
}
