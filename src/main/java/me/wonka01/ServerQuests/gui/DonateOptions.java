package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
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

    public Inventory inventory;
    private DonateQuestGui donateQuestGui;

    public DonateOptions(DonateQuestGui donateQuestGui) {
        inventory = Bukkit.createInventory(this, 36, ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getViewMenu()));
        this.donateQuestGui = donateQuestGui;
    }

    @Override
    public void initializeItems() {

    }

    public void initializeItems(Player player) {
        inventory.clear();
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        int index = 0;
        for (QuestController controller : controllers) {
            if(controller.getObjectiveType().equals(ObjectiveType.GUI)) {
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
            lore.add(ChatColor.translateAlternateColorCodes('&',LanguageConfig.getConfig().getMessages().getTimeRemaining() + questData.getQuestDuration()));
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
        for (QuestController controller: controllers) {
            if(!controller.getObjectiveType().equals(ObjectiveType.GUI)){
                continue;
            }
            if(clickedSlot == count) {
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

