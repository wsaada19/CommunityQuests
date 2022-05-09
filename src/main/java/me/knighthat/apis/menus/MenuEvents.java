package me.knighthat.apis.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuEvents implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {

        Inventory topInventory = event.getView().getTopInventory();

        if (topInventory.getHolder() instanceof Menu) {

            Menu menu = (Menu) topInventory.getHolder();
            event.setCancelled(true);

            if (event.getCurrentItem() != null)
                menu.onItemClick(event);
        }
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {

        InventoryHolder instance = event.getInventory().getHolder();

        if (instance instanceof Menu)
            ((Menu) instance).onClose(event);
    }
}