package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.enums.PermissionConstants;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.UUID;

public class ToggleBarCommand extends SubCommand {
    public HashMap<UUID, PermissionAttachment> map = new HashMap<UUID, PermissionAttachment>();

    @Override
    public void onCommand(Player player, String[] args) {
        // always hidden if player has this perm
        if (player.hasPermission(PermissionConstants.HIDE_BAR)) {
            return;
        }
        BarManager.toggleShowPlayerBar(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
        throw new NotImplementedException();
    }
}
