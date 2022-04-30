package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.configuration.messages.Messages;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StartCommand extends SubCommand {

    protected StartCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.START_QUEST;
    }

    public void onCommand(Player player, String[] args) {

        if (args.length < 2) {
            JavaPlugin.getPlugin(ServerQuests.class).getStartGui().openInventory(player);
            return;
        }

        startFromCommand(player, args);

    }

    public void onCommand(CommandSender sender, String[] args) {

        if (args.length < 2) {
            return;
        }

        startFromCommand(sender, args);
    }

    private void startFromCommand(CommandSender sender, String[] args) {
        Messages messages = LanguageConfig.getConfig().getMessages();

        String questId = args[1];
        QuestModel questModel = JavaPlugin.getPlugin(ServerQuests.class).questLibrary.getQuestModelById(questId);
        if (questModel == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getInvalidQuestName()));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getInvalidQuestType()));
            return;
        }
        EventType eventType;

        if (args[2].equalsIgnoreCase("coop")) {
            eventType = EventType.COLLAB;
            if (questModel.getQuestGoal() <= 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getCoopQuestWithoutGoalErrorMessage()));
                return;
            }
        } else if (args[2].equalsIgnoreCase("comp")) {
            eventType = EventType.COMPETITIVE;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getInvalidQuestType()));
            return;
        }

        boolean questCreated = ActiveQuests.getActiveQuestsInstance().beginNewQuest(questModel, eventType);
        if (!questCreated) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getQuestLimitReached()));
        }
    }
}
