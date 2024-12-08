package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.gui.StartMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand extends PluginCommand {

    public StartCommand(ServerQuests plugin) {
        super(plugin, false);
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
                    break;
                case "comp":
                    type = EventType.COMPETITIVE;
                    break;
                case "coll":
                    type = EventType.COLLECTIVE;
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 2) {
            // Fetch all available quest models and filter based on partial input
            List<QuestModel> questModels = getPlugin().config().getQuestLibrary().getAllQuestModels();
            completions = questModels.stream()
                    .map(QuestModel::getQuestId)
                    .filter(id -> id.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Handle quest type completions (third argument)
        if (args.length == 3) {
            List<String> questTypes = Arrays.asList("coop", "comp", "coll");
            completions = questTypes.stream()
                    .filter(type -> type.startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return completions;
    }
}
