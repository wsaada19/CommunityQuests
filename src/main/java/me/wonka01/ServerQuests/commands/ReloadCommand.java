package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends PluginCommand {

    public ReloadCommand(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public @NonNull String getName() {
        return "reload";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.reload";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        getPlugin().config().reload();
        getPlugin().config().initializeVariables();
        getPlugin().config().initializeQuests();
        if (getPlugin().getHologram() != null) {
            if (getPlugin().getConfig().getBoolean("hologram.enabled")) {
                getPlugin().getHologram().reloadConfig();
                getPlugin().getHologram().displayHologram();
            } else {
                getPlugin().getHologram().removeHologram();
            }
        }

        getPlugin().messages().reload();

        String reloadMessage = getPlugin().messages().message("reloadCommand");
        sender.sendMessage(reloadMessage);
    }
}
