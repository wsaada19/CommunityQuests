package me.wonka01.ServerQuests.commands;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand implements Colorization {

    @Getter
    private final ServerQuests plugin;

    protected SubCommand(ServerQuests plugin) {
        this.plugin = plugin;
    }

    public abstract @NonNull String getPermission();

    public abstract void onCommand(Player player, String[] args);

    public abstract void onCommand(CommandSender player, String[] args);
}
