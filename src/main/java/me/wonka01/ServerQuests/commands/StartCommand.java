package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.handlers.EventTypeHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StartCommand extends SubCommand {

    public void onCommand(Player player, String[] args) {

        if(!player.hasPermission("serverevents.start")){
            player.sendMessage("You do not have permission to perform this action");
            return;
        }

        if(args.length < 2)
        {
            JavaPlugin.getPlugin(ServerQuests.class).getStartGui().openInventory(player);
            return;
        }

        String questId = args[1];
        QuestModel questModel = JavaPlugin.getPlugin(ServerQuests.class).questLibrary.getQuestModelById(questId);
        if(questModel == null){
            player.sendMessage("The name you entered does not exist");
            return;
        }

        if(args.length < 3){
            player.sendMessage(ChatColor.RED + "Please enter a quest type; collab or competetive");
            return;
        }
        if(args[2].equalsIgnoreCase("coop"))
        {
            ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(questModel, EventTypeHandler.EventType.COLLAB);
            return;
        }

        if(args[2].equalsIgnoreCase("competitive"))
        {
            ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(questModel, EventTypeHandler.EventType.COMPETITIVE);
            return;
        }

        player.sendMessage(ChatColor.RED + "Please enter a quest type: coop or competetive");

    }
}
