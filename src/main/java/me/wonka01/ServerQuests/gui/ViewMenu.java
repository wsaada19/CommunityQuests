package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ViewMenu extends Menu {

    private int schedulerId;

    public ViewMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "viewQuests", 36);
        this.schedulerId = createScheduler();
    }

    @Override
    protected void setContents() {
        int index = 0;
        for (QuestController controller : getControllers()) {
            getInventory().setItem(index, controller.isCompetitive() ? compItem(controller) : coopItem(controller));
            index++;
        }
    }

    private @NonNull ItemStack coopItem(@NonNull QuestController controller) {
        QuestData data = controller.getQuestData();
        List<String> lore = super.getLoreFromData(data);
        double goal = data.getQuestGoal();
        if (goal > 0) {
            String completed = Utils.decimalToString(data.getAmountCompleted()),
                    progressStr = getPlugin().messages().string("progress");
            progressStr += " &f" + completed + "/" + Utils.decimalToString(goal);
            lore.add(progressStr);
            lore.add(data.getProgressIndicator());
            lore.add("");
            if (data.getObjectives().size() > 1) {
                getObjectiveProgress(data.getObjectives(), lore);
                lore.add("");
            }
            lore.add(getPlugin().messages().string("topContributors"));
            getTopPlayerString(controller, lore);
        }
        lore.add(getPlayerProgress(controller));

        addRewardDisplay(lore, controller.getQuestData());

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private void addRewardDisplay(List<String> lore, QuestData data) {
        if (data.getRewardDisplay() == null || data.getRewardDisplay().isEmpty()) {
            return;
        }
        lore.add("");
        for (String reward : data.getRewardDisplay()) {
            lore.add(color(reward));
        }
    }

    private @NonNull ItemStack compItem(QuestController ctrl) {
        QuestData data = ctrl.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        String leaders = getPlugin().messages().string("topContributors");
        lore.add(leaders);
        getTopPlayerString(ctrl, lore);
        lore.add(getPlayerProgress(ctrl));
        if (data.getObjectives().size() > 1) {
            lore.add("");
            getObjectivePlayerProgress(data.getObjectives(), lore, getOwner(), (CompetitiveQuestData) data);
        }

        addRewardDisplay(lore, data);

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private int createScheduler() {
        // refresh every 1 second
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), this::refreshMenu, 0L, 20L);
    }

    private void refreshMenu() {
        getInventory().clear();
        setContents();
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        Bukkit.getScheduler().cancelTask(schedulerId);
    }

    private void getTopPlayerString(QuestController controller, List<String> lore) {
        String noData = "&7n/a";
        int size = BasePlayerComponent.getLeaderBoardSize();
        ArrayList<PlayerData> topPlayers = controller.getPlayerComponent().getTopPlayers(size);

        if (topPlayers.size() < 1) {
            lore.add(color(noData));
            return;
        }

        for (int i = 0; i < topPlayers.size(); i++) {
            PlayerData topPlayer = topPlayers.get(i);
            if (topPlayer == null) {
                break;
            }

            String playerString = "&e" + (i + 1) + ")&f " + topPlayer.getName() + "&f - &6&l";
            playerString += Utils.decimalToString(topPlayer.getAmountContributed());
            lore.add(color(playerString));
        }
    }

    private void getObjectiveProgress(List<Objective> objectives, List<String> lore) {
        for (Objective obj : objectives) {
            lore.add(color(obj.getDescription() + " &f" + Utils.decimalToString(obj.getAmountComplete()) + "/"
                    + Utils.decimalToString(obj.getGoal())));
        }
    }

    private void getObjectivePlayerProgress(List<Objective> objectives, List<String> lore, Player player,
            CompetitiveQuestData questData) {
        for (int i = 0; i < objectives.size(); i++) {
            Objective obj = objectives.get(i);
            lore.add(color(obj.getDescription() + " &f"
                    + Utils.decimalToString(questData.getPlayers().getAmountContributedByObjectiveId(player, i)) + "/"
                    + Utils.decimalToString(obj.getGoal())));
        }
    }

    private @NonNull String getPlayerProgress(@NonNull QuestController ctrl) {
        double progress = ctrl.getPlayerComponent().getAmountContributed(getOwner());
        String progressStr = getPlugin().messages().string("you");
        progressStr += " -&6&l " + Utils.decimalToString(progress);
        return color(progressStr);
    }
}
