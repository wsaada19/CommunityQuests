package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.gui.StartMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand extends PluginCommand {

    public StartCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "start";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.start";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length >= 3) {
            QuestModel model = getPlugin().config().getQuestLibrary().getQuestModelById(args[1]);
            if (model == null) {
                String invalidName = getPlugin().messages().message("invalidQuestName");
                sender.sendMessage(invalidName);
                return;
            }

            EventType type;
            switch (args[2]) {
                case "coop":
                    type = EventType.COLLAB;
                    if (model.getQuestGoal() <= 0) {
                        String noGoal = getPlugin().messages().message("cooperativeQuestMustHaveAGoal");
                        sender.sendMessage(noGoal);
                        return;
                    }
                    break;
                case "comp":
                    type = EventType.COMPETITIVE;
                    break;
                default:
                    String invalidQuestType = getPlugin().messages().message("invalidQuestType");
                    sender.sendMessage(invalidQuestType);
                    return;
            }

            if (!ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, type)) {
                String reachLimit = getPlugin().messages().message("questLimitReached");
                sender.sendMessage(reachLimit);
            }
        } else if (args.length == 2) {
            String invalidQuestType = getPlugin().messages().message("invalidQuestType");
            sender.sendMessage(invalidQuestType);
        } else if (sender instanceof Player) {

            new StartMenu(getPlugin(), (Player) sender).open();
        }
    }
}
