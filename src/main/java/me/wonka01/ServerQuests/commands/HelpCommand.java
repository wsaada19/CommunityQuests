package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.knighthat.apis.commands.PluginCommand;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends PluginCommand {
    
    public HelpCommand(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public @NonNull String getName() {
        return "help";
    }

    @Override
    public @NonNull String getPermission() {
        return "";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        getPlugin().getMessages().message("helpMessage");
    }
}
