package me.knighthat.apis.commands;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;

@Getter
public abstract class PluginCommand implements Colorization {

    private final ServerQuests plugin;
    private final boolean requiresPlayer;

    protected PluginCommand(ServerQuests plugin, boolean requiresPlayer) {
        this.plugin = plugin;
        this.requiresPlayer = requiresPlayer;
    }

    public abstract @NonNull String getName();

    public abstract @NonNull String getPermission();

    public abstract void execute(@NonNull CommandSender sender, @NonNull String[] args);
}
