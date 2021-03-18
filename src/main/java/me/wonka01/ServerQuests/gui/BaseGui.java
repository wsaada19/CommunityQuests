package me.wonka01.ServerQuests.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class BaseGui {

    public BaseGui() {
    }

    public abstract void initializeItems();

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
