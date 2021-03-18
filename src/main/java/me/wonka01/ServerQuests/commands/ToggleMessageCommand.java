package me.wonka01.ServerQuests.commands;

import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ToggleMessageCommand extends SubCommand {
    public HashMap<UUID, PermissionAttachment> map = new HashMap<UUID, PermissionAttachment>();

    public void onCommand(Player player, String[] args) {
        PermissionAttachment attachment = player.addAttachment(JavaPlugin.getPlugin(ServerQuests.class));
        if (player.hasPermission("serverquests.showmessages")) {
            attachment.setPermission("serverquests.showmessages", false);
            player.sendMessage(ChatColor.YELLOW + "You will no longer see server quest messages");
        } else {
            attachment.setPermission("serverquests.showmessages", true);
            player.sendMessage(ChatColor.YELLOW + "You will now see server quest messages");
        }

        map.put(player.getUniqueId(), attachment);
    }
}
