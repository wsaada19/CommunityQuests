package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ViewMenu extends Menu {

    public ViewMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "viewQuests", 36);
    }

    @Override
    protected void setContents() {
        for (QuestController controller : getControllers())
            getInventory().addItem(controller.isCompetitive() ? compItem(controller) : coopItem(controller));
    }

    private @NonNull ItemStack coopItem(@NonNull QuestController controller) {
        QuestData data = controller.getQuestData();
        List<String> lore = super.getLoreFromData(data);

        if (data.getQuestGoal() > 0) {
            String completed = Utils.decimalToString(data.getAmountCompleted()),
                    progressStr = getPlugin().messages().string("progress");
            progressStr += " &f" + completed + "/" + (int) data.getQuestGoal();
            lore.add(progressStr);
            lore.add(getProgressIndicator((int) data.getAmountCompleted(), (int) data.getQuestGoal()));
            lore.add("");
            if (data.getObjectives().size() > 1) {
                getObjectiveProgress(data.getObjectives(), lore);
                lore.add("");
            }
            lore.add(getPlugin().messages().string("topContributors"));
            getTopPlayerString(controller, lore);
        }
        lore.add(getPlayerProgress(controller));

        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
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
        return super.createItemStack(data.getDisplayItem(), data.getDisplayName(), lore);
    }

    private void getTopPlayerString(QuestController controller, List<String> lore) {
        String noData = "&7n/a";
        ArrayList<PlayerData> top3Players = controller.getPlayerComponent().getTopPlayers(3);

        if (top3Players.size() < 1) {
            lore.add(color(noData));
            return;
        }

        for (int i = 0; i < top3Players.size(); i++) {
            PlayerData topPlayer = top3Players.get(i);
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

    private String getProgressIndicator(int completed, int goal) {
        StringBuilder speedDisplay = new StringBuilder();
        double ratio = completed / (double) goal;

        int result = 40 - (int) (40.0 - (ratio * 40.0));
        for (int i = 0; i < 40; i++) {
            if (i < result) {
                speedDisplay.append(ChatColor.GREEN);
                speedDisplay.append("|");
            } else {
                speedDisplay.append(ChatColor.GRAY);
                speedDisplay.append("|");
            }
        }
        return color(speedDisplay.toString());
    }

    private @NonNull String getPlayerProgress(@NonNull QuestController ctrl) {
        double progress = ctrl.getPlayerComponent().getAmountContributed(getOwner());
        String progressStr = getPlugin().messages().string("you");
        progressStr += " -&6&l " + Utils.decimalToString(progress);
        return color(progressStr);
    }
}
