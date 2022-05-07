package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
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

        getPlugin().reloadConfiguration();
        getPlugin().getMessages().reload();

        String reloadMessage = getPlugin().getMessages().message("reloadCommand");
        sender.sendMessage(reloadMessage);
    }
}
