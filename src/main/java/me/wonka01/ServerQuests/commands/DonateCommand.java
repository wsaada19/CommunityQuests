package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.gui.DonateMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DonateCommand extends PluginCommand {

    public DonateCommand(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public @NonNull String getName() {
        return "donate";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.donate";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        List<QuestController> controllerList = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        if (args.length > 1) {
            if (!sender.hasPermission("communityquests.donate.others") && (sender instanceof Player)) {
                String noPermission = getPlugin().messages().message("noPermission");
                sender.sendMessage(noPermission);
                return;
            }

            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);
            if (target != null && target.isOnline()) {
                for (QuestController controller : controllerList)
                    if (controller.getObjectiveTypes().contains(ObjectiveType.GUI)) {
                        new DonateMenu(getPlugin(), target).open();
                        return;
                    }
            } else {
                String playerNotOnline = getPlugin().messages().message("playerNotOnline");
                sender.sendMessage(playerNotOnline);
            }
            return;
        }

        Player player = (Player) sender;
        for (QuestController controller : controllerList)
            if (controller.getObjectiveTypes().contains(ObjectiveType.GUI)) {
                new DonateMenu(getPlugin(), player).open();
                return;
            }

        String noActiveQuest = getPlugin().messages().message("noActiveDonateQuests");
        player.sendMessage(noActiveQuest);
    }
}
