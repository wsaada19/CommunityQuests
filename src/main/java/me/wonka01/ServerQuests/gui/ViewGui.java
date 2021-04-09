package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ViewGui extends BaseGui implements Listener, InventoryHolder {

    public Inventory inventory;

    public ViewGui() {
        inventory = Bukkit.createInventory(this, 36, "Active Quests");
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
        int progress = controller.getQuestData().getAmountCompleted();
        int goal = controller.getQuestData().getQuestGoal();

        String progressString = ChatColor.GRAY + "Progress: " + ChatColor.GREEN + progress + "/" + goal;

        ItemStack item = createGuiItem(Material.DIAMOND,
                ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDisplayName()),
                ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDescription()),
                "",
                progressString,
                getPlayerProgress(controller, player));

        inventory.setItem(index, item);
    }

    public void createItemStackForComp(QuestController controller, int index, Player player) {

        int goal = controller.getQuestData().getQuestGoal();
        ItemStack item;
        String leaders = ChatColor.GRAY + "Leaders";
        PlayerData topPlayer = controller.getPlayerComponent().getTopPlayerData();
        if(topPlayer == null) {
            item = createGuiItem(Material.DIAMOND,
                    ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDisplayName()),
                    ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDescription()),
                    "",
                    leaders,
                    ChatColor.GRAY + "none",
                    getPlayerProgress(controller, player));
        } else {
            item = createGuiItem(Material.DIAMOND,
                    ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDisplayName()),
                    ChatColor.translateAlternateColorCodes('&', controller.getQuestData().getDescription()),
                    "",
                    leaders,
                    ChatColor.GRAY + topPlayer.getDisplayName() + ": " + ChatColor.GREEN + topPlayer.getAmountContributed() + "/" + goal,
                    getPlayerProgress(controller, player));
        }

        inventory.setItem(index, item);
    }

    private String getPlayerProgress(QuestController controller, Player player) {
        int playerProgress = controller.getPlayerComponent().getAmountContributed(player);
        return (ChatColor.GRAY + "You: " + ChatColor.GREEN + playerProgress);
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
