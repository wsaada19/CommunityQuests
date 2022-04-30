package me.wonka01.ServerQuests.commands;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.enums.PermissionNode;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class DonateQuestCommand extends SubCommand {

    protected DonateQuestCommand(ServerQuests plugin) {
        super(plugin);
    }

    @Override
    public @NonNull String getPermission() {
        return PermissionNode.DONATE;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        List<QuestController> controllerList = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        for (QuestController controller : controllerList) {
            if (controller.getObjectiveType() == ObjectiveType.GUI) {
                JavaPlugin.getPlugin(ServerQuests.class).getQuestsGui().openInventory(player);
                return;
            }
        }
        String noActiveQuest = getPlugin().getMessages().message("noActiveDonateQuests");
        player.sendMessage(noActiveQuest);
    }

    public void onCommand(CommandSender sender, String[] args) {
    }
}
