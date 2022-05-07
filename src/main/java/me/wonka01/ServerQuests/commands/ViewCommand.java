package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ViewCommand extends PluginCommand {
    public ViewCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "view";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.view";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        Player player = (Player) sender;

        if (ActiveQuests.getActiveQuestsInstance().getActiveQuestsList().size() < 1) {

            String noActiveQuestMessage = getPlugin().getMessages().message("noActiveQuests");
            player.sendMessage(noActiveQuestMessage);
            return;
        }

        ViewGui view = getPlugin().getViewGui();
        view.initializeItems(player);
        view.openInventory(player);
    }
}
