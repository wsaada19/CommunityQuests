package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.gui.ViewGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ViewQuestsCommand extends SubCommand {

    public ViewQuestsCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.VIEW_QUEST;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if (ActiveQuests.getActiveQuestsInstance().getActiveQuestsList().size() < 1) {

            String noActiveQuestMessage = getPlugin().getMessages().message("noActiveQuests");
            player.sendMessage(noActiveQuestMessage);
            return;
        }
        ViewGui view = JavaPlugin.getPlugin(ServerQuests.class).getViewGui();
        view.initializeItems(player);
        view.openInventory(player);
    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
