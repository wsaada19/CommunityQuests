package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleMessageCommand extends PluginCommand {

    // public HashMap<UUID, PermissionAttachment> map = new HashMap<UUID,
    // PermissionAttachment>();

    public ToggleMessageCommand(ServerQuests plugin) {
        super(plugin, true);
    }

    @Override
    public @NonNull String getName() {
        return "togglemessage";
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.showmessages";
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        // PermissionAttachment attachment =
        // player.addAttachment(JavaPlugin.getPlugin(ServerQuests.class));
        // if (player.hasPermission("serverquests.showmessages")) {
        // attachment.setPermission("serverquests.showmessages", false);
        // player.sendMessage(ChatColor.YELLOW + "You will no longer see server quest
        // messages");
        // } else {
        // attachment.setPermission("serverquests.showmessages", true);
        // player.sendMessage(ChatColor.YELLOW + "You will now see server quest
        // messages");
        // }
        //
        // map.put(player.getUniqueId(), attachment);
    }
}
