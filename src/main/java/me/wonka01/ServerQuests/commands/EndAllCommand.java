package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EndAllCommand extends PluginCommand {

    public EndAllCommand(ServerQuests plugin) {
        super(plugin, false);
    }

    @Override
    public @NonNull String getName() {
        return "endall";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.stop";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        ActiveQuests.getActiveQuestsInstance().endAllQuests();
        sender.sendMessage(getPlugin().messages().message("endAllQuestsMessage"));
    }
}
