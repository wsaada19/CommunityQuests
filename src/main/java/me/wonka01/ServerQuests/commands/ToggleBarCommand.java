package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
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
        return "communityquests.bossbar.hide";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        BarManager.toggleShowPlayerBar((Player) sender);
    }
}
