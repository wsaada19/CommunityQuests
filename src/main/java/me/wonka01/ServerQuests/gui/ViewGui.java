package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import me.wonka01.ServerQuests.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.util.ArrayList;
import java.util.List;

public class ViewGui extends BaseGui implements Listener, InventoryHolder {

    public Inventory inventory;

    public ViewGui() {
        inventory = Bukkit.createInventory(this, 36, ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getViewMenu()));
    }

    @Override
    public void initializeItems() {

    }

    public void initializeItems(Player player) {
        inventory.clear();
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        int index = 0;
        for (QuestController controller : controllers) {
            if (controller.isCompetitive()) {
                createItemStackForComp(controller, index, player);
            } else {
                createItemStackForCoop(controller, index, player);
            }
            index++;
        }
    }

    public void createItemStackForCoop(QuestController controller, int index, Player player) {
        String progress = NumberUtil.getNumberDisplay(controller.getQuestData().getAmountCompleted());

        int goal = controller.getQuestData().getQuestGoal();

        String progressString = LanguageConfig.getConfig().getMessages().getProgress() + ": " + ChatColor.GREEN + progress + "/" + goal;
        ArrayList<String> lore = getQuestDisplay(controller.getQuestData());

        if (goal > 0) {
            lore.add(progressString);
        }
        lore.add(getPlayerProgress(controller, player));

        inventory.setItem(index, createGuiItem(Material.DIAMOND,
            ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDisplayName()),
            lore.toArray(new String[0])));
    }

    public void createItemStackForComp(QuestController controller, int index, Player player) {

        int goal = controller.getQuestData().getQuestGoal();
        String leaders = LanguageConfig.getConfig().getMessages().getLeader();
        PlayerData topPlayer = controller.getPlayerComponent().getTopPlayerData();

        ArrayList<String> lore = getQuestDisplay(controller.getQuestData());

        lore.add(leaders);
        if (topPlayer == null) {
            lore.add("&7n/a");
        } else {
            String leaderString = "&7" + topPlayer.getDisplayName() + ": " + "&a" + NumberUtil.getNumberDisplay(topPlayer.getAmountContributed());
            if (goal > 0) {
                leaderString += "/" + goal;
            }
            lore.add(leaderString);
        }
        lore.add(getPlayerProgress(controller, player));

        inventory.setItem(index, createGuiItem(Material.DIAMOND, controller.getQuestData().getDisplayName(), lore.toArray(new String[0])));
    }

    private ArrayList<String> getQuestDisplay(QuestData questData) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', questData.getDescription()));
        lore.add("");
        if (questData.getQuestDuration() > 0) {
            lore.add(ChatColor.translateAlternateColorCodes('&',LanguageConfig.getConfig().getMessages().getTimeRemaining() + questData.getQuestDuration()));
            lore.add("");
        }
        return lore;
    }

    private String getPlayerProgress(QuestController controller, Player player) {
        double playerProgress = controller.getPlayerComponent().getAmountContributed(player);
        return (ChatColor.GRAY + LanguageConfig.getConfig().getMessages().getYou() + ": " + ChatColor.GREEN + NumberUtil.getNumberDisplay(playerProgress));
    }

    public void openInventory(Player player) {

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }
        e.setCancelled(true);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
