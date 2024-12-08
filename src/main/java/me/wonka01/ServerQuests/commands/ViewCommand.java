package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;

import java.util.List;

import org.bukkit.command.Command;
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

        if (ActiveQuests.getActiveQuestsInstance().getActiveQuestsList().isEmpty()) {
            String noActiveQuestMessage = getPlugin().messages().message("noActiveQuests");
            player.sendMessage(noActiveQuestMessage);
            return;
        }
        new ViewMenu(getPlugin(), player).open();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
