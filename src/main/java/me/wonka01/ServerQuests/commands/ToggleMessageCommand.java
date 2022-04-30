package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessageCommand extends SubCommand {
    public ToggleMessageCommand(ServerQuests plugin) {
        super(plugin);
    }
    //public HashMap<UUID, PermissionAttachment> map = new HashMap<UUID, PermissionAttachment>();

    @Override
    public @NonNull String getPermission() {
        return null;
    }

    public void onCommand(Player player, String[] args) {
//        PermissionAttachment attachment = player.addAttachment(JavaPlugin.getPlugin(ServerQuests.class));
//        if (player.hasPermission("serverquests.showmessages")) {
//            attachment.setPermission("serverquests.showmessages", false);
//            player.sendMessage(ChatColor.YELLOW + "You will no longer see server quest messages");
//        } else {
//            attachment.setPermission("serverquests.showmessages", true);
//            player.sendMessage(ChatColor.YELLOW + "You will now see server quest messages");
//        }
//
//        map.put(player.getUniqueId(), attachment);
    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
