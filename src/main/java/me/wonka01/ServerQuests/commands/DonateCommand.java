package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.gui.DonateMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DonateCommand extends PluginCommand {

    public DonateCommand(ServerQuests plugin) {
        super(plugin, true);
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
        Player player = (Player) sender;

        List<QuestController> controllerList = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        for (QuestController controller : controllerList)
            if (controller.getObjective().equals(ObjectiveType.GUI)) {
                new DonateMenu(getPlugin(), player).open();
                return;
            }

        String noActiveQuest = getPlugin().messages().message("noActiveDonateQuests");
        player.sendMessage(noActiveQuest);
    }
}
