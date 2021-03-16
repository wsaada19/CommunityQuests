package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.gui.BaseGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiEvent extends QuestListener implements Listener, InventoryHolder {

    private Inventory inventory;
    private int[] glassLocations = {12, 13, 14, 21, 23, 30, 31, 32};

    public GuiEvent(ActiveQuests activeQuests) {

        super(activeQuests);
        inventory = Bukkit.createInventory(this, 45, "Place item in the center to ");
        initializeItems();
    }

    private void initializeItems() {
        ItemStack glass = createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "", "");
        for (int index : glassLocations) {
            inventory.setItem(index, glass);
        }
    }

    public Inventory getInventory() {
        return null;
    }

    protected ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for (String lorecomments : lore) {

            metalore.add(lorecomments);
        }

        meta.setLore(metalore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    protected boolean clickEventCheck(InventoryClickEvent e, BaseGui holder) {
        if (e.getInventory().getHolder() != holder) {
            return false;
        }
        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return false;

        return true;
    }

}
