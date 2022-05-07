package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.gui.StopGui;
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
        StopGui stopGui = getPlugin().getStopGui();
        stopGui.initializeItems();
        stopGui.openInventory((Player) sender);
    }
}
