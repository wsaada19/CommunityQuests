package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleBarCommand extends SubCommand {

    public ToggleBarCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.HIDE_BAR;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        BarManager.toggleShowPlayerBar(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
