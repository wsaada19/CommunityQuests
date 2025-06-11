package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleBarCommand extends PluginCommand {

    public ToggleBarCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "togglebar";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.togglebar";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        BarManager.toggleShowPlayerBar((Player) sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
