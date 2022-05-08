package me.knighthat.apis.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MenuEvents implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {

        Inventory topInventory = event.getView().getTopInventory();

        if (topInventory.getHolder() instanceof Menu) {

            Menu menu = (Menu) topInventory;
            event.setCancelled(true);

            if (event.getCurrentItem() != null)
                menu.onItemClick(event);
        }
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {

        if (event.getInventory().getHolder() instanceof Menu)
            ((Menu) event.getInventory()).onClose(event);
    }
}
