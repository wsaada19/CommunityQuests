package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.gui.StopGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StopQuestCommand extends SubCommand {

    protected StopQuestCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.STOP_QUEST;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        StopGui stopGui = JavaPlugin.getPlugin(ServerQuests.class).getStopGui();
        stopGui.initializeItems();
        stopGui.openInventory(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
