package me.wonka01.ServerQuests.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;


public class BossBarGui extends BaseGui implements Listener, InventoryHolder {


    public void initializeItems() {

    }

    public Inventory getInventory() {
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }
    }
}
