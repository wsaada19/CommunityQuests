package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    public HelpCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return "";
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(getHelpMessage());
    }

    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(getHelpMessage());
    }

    private @NonNull String getHelpMessage() {
        return getPlugin().getMessages().message("helpMessage");
    }
}
