package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.utils.NumberUtils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ViewMenu extends Menu {

    public ViewMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "viewQuests", 36);
    }

    @Override
    protected void setContents() {
        for (QuestController controller : getControllers())
            getInventory().addItem(controller.isCompetitive() ? coopItem(controller) : compItem(controller));
    }

    private @NonNull ItemStack coopItem(@NonNull QuestController controller) {
        QuestData data = controller.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        if (data.getQuestGoal() > 0) {

            String completed = NumberUtils.decimals(data.getAmountCompleted()),
                progressStr = getPlugin().messages().string("progress");
            progressStr += ": &a" + completed + "/" + data.getQuestGoal();

            lore.add(progressStr);
        }
        lore.add(getPlayerProgress(controller));

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private @NonNull ItemStack compItem(QuestController ctrl) {
        QuestData data = ctrl.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        String leaders = getPlugin().messages().string("leader");
        lore.add(leaders);

        String topsList = "&7n/a";
        PlayerData topPlayers = ctrl.getPlayerComponent().getTopPlayerData();
        if (topPlayers != null && data.getQuestGoal() > 0) {

            topsList = "&7" + topPlayers.getName() + ": &a";
            topsList += NumberUtils.decimals(topPlayers.getAmountContributed()) + "/" + data.getQuestGoal();
        }

        lore.add(color(topsList));
        lore.add(getPlayerProgress(ctrl));

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private @NonNull String getPlayerProgress(@NonNull QuestController ctrl) {
        double progress = ctrl.getPlayerComponent().getAmountContributed(getOwner());
        String progressStr = getPlugin().messages().string("you");
        progressStr += ": &a" + NumberUtils.decimals(progress);
        return color(progressStr);
    }
}
