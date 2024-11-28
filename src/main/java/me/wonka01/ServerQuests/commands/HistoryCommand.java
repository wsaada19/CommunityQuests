package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.ViewHistoryMenu;

import org.bukkit.Bukkit;
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
        Bukkit.getLogger().info("History command executed by " + player.getName());
        new ViewHistoryMenu(getPlugin(), player).open();
    }
}
