package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
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

public class DonateOptions extends BaseGui implements Listener, InventoryHolder {

    private final DonateQuestGui donateQuestGui;
    private final ServerQuests plugin;
    public Inventory inventory;

    public DonateOptions(ServerQuests plugin, DonateQuestGui donateQuestGui) {
        this.plugin = plugin;
        this.inventory = createInventory();
        this.donateQuestGui = donateQuestGui;
    }

    private @NonNull Inventory createInventory() {

        String title = plugin.getMessages().string("donateMenu");
        return Bukkit.createInventory(this, 36, title);
    }

    @Override
    public void initializeItems() {
        inventory.clear();
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        int index = 0;
        for (QuestController controller : controllers) {
            if (controller.getObjectiveType().equals(ObjectiveType.GUI)) {
                createItemStack(controller, index);
                index++;
            }
        }
    }

    public void createItemStack(QuestController controller, int index) {
        ArrayList<String> lore = getQuestDisplay(controller.getQuestData());
        lore.add("&eClick to donate to this quest");
        inventory.setItem(index, createGuiItem(Material.DIAMOND, controller.getQuestData().getDisplayName(), lore.toArray(new String[0])));
    }

    private ArrayList<String> getQuestDisplay(QuestData questData) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', questData.getDescription()));
        lore.add("");
        if (questData.getQuestDuration() > 0) {

            String remaining = plugin.getMessages().string("timeRemaining");
            remaining += questData.getQuestDuration();
            lore.add(remaining);
            lore.add("");
        }
        return lore;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();

        int clickedSlot = e.getRawSlot();
        QuestController donateController = null;
        int count = 0;

        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        for (QuestController controller : controllers) {
            if (!controller.getObjectiveType().equals(ObjectiveType.GUI)) {
                continue;
            }
            if (clickedSlot == count) {
                donateController = controller;
                break;
            }
            count++;
        }

        if (donateController == null) {
            return;
        }
        donateQuestGui.openInventory(player);
    }

    public Inventory getInventory() {
        return inventory;
    }
}

