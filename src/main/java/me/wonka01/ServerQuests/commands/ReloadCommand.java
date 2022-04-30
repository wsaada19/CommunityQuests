package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.RELOAD_QUEST;
    }

    public void onCommand(Player player, String[] args) {
        reload(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        reload(sender);
    }

    private void reload(CommandSender sender) {

        getPlugin().reloadConfiguration();
        getPlugin().getMessages().reload();

        String reloadMessage = getPlugin().getMessages().message("reloadCommand");
        sender.sendMessage(reloadMessage);
    }
}
