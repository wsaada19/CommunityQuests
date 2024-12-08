package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewHistoryMenu;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HistoryCommand extends PluginCommand {
    public HistoryCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "history";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.view";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        Player player = (Player) sender;
        new ViewHistoryMenu(getPlugin(), player).open();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }
}
