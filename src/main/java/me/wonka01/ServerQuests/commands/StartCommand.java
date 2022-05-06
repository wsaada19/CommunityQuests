package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
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

        if (args.length < 3) {
            String invalidQuestType = getPlugin().getMessages().message("invalidQuestType");
            sender.sendMessage(invalidQuestType);
            return;
        }

        QuestModel model = getPlugin().questLibrary.getQuestModelById(args[1]);

        if (model == null) {
            String invalidName = getPlugin().getMessages().message("invalidQuestName");
            sender.sendMessage(invalidName);
            return;
        }

        EventType type;
        switch (args[2]) {
            case "coop":
                type = EventType.COLLAB;
                if (model.getQuestGoal() <= 0) {
                    String noGoal = getPlugin().getMessages().message("cooperativeQuestMustHaveAGoal");
                    sender.sendMessage(noGoal);
                    return;
                }
                break;
            case "comp":
                type = EventType.COMPETITIVE;
                break;
            default:
                String invalidQuestType = getPlugin().getMessages().message("invalidQuestType");
                sender.sendMessage(invalidQuestType);
                return;
        }

        if (!ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, type)) {
            String reachLimit = getPlugin().getMessages().message("questLimitReached");
            sender.sendMessage(reachLimit);
        }
    }
}
