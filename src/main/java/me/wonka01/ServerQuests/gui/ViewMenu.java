package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
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

        for (QuestController ctrl : getControllers())
            getInventory().addItem(ctrl.isCompetitive() ? coopItem(ctrl) : compItem(ctrl));
    }

    private @NonNull ItemStack coopItem(@NonNull QuestController ctrl) {

        QuestData data = ctrl.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        if (data.getQuestGoal() > 0) {

            String completed = Utils.decimals(data.getAmountCompleted(), 1),
                progressStr = getPlugin().messages().string("progress");
            progressStr += ": &a" + completed + "/" + data.getQuestGoal();

            lore.add(progressStr);
        }
        lore.add(getPlayerProgress(ctrl));

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private @NonNull ItemStack compItem(QuestController ctrl) {

        QuestData data = ctrl.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        String leaders = getPlugin().messages().string("leader");
        lore.add(leaders);

        String topsList = "&7n/a";
        PlayerData tops = ctrl.getPlayerComponent().getTopPlayerData();
        if (tops != null && data.getQuestGoal() > 0) {

            topsList = "&7" + tops.getDisplayName() + ": &a";
            topsList += Utils.decimals(tops.getAmountContributed(), 1) + "/" + data.getQuestGoal();
        }

        lore.add(color(topsList));
        lore.add(getPlayerProgress(ctrl));

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private @NonNull String getPlayerProgress(@NonNull QuestController ctrl) {

        double progress = ctrl.getPlayerComponent().getAmountContributed(getOwner());
        String progressStr = getPlugin().messages().string("you");
        progressStr += ": &a" + Utils.decimals(progress, 1);
        return color(progressStr);
    }
}
