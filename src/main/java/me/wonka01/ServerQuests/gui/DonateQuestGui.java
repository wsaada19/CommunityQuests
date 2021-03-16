package me.wonka01.ServerQuests.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class DonateQuestGui extends BaseGui implements InventoryHolder, Listener {
    private Inventory inventory;
    private int[] glassLocations = {12, 13, 14, 21, 23, 30, 31, 32};

    public DonateQuestGui() {
        inventory = Bukkit.createInventory(this, 45, "Place item in the center to ");
        initializeItems();
    }

    public void initializeItems() {
        ItemStack glass = createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "", "");
        for (int index : glassLocations) {
            inventory.setItem(index, glass);
        }
    }

    public Inventory getInventory() {
        return null;
    }
}
