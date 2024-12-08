package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.StopMenu;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopCommand extends PluginCommand {
    public StopCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "stop";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.stop";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        new StopMenu(getPlugin(), (Player) sender).open();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
