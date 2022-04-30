package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.events.GuiEvent;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class DonateQuestGui extends BaseGui implements InventoryHolder, Listener {

    private final int ITEM_SLOT = 22;
    private final ServerQuests plugin;
    private final Inventory inventory;
    private final int[] GLASS_LOCATIONS = {12, 13, 14, 21, 23, 30, 31, 32};
    private final GuiEvent eventHandler;

    public DonateQuestGui(ServerQuests plugin) {
        this.plugin = plugin;
        this.inventory = createInventory();
        initializeItems();
        eventHandler = new GuiEvent(ActiveQuests.getActiveQuestsInstance());
    }

    private @NonNull Inventory createInventory() {

        String title = plugin.getMessages().string("donateMenu");
        return Bukkit.createInventory(this, 45, title);
    }

    public void initializeItems() {
        ItemStack glass = createGuiItem(Material.DIAMOND_BLOCK, " ", "");
        for (int index : GLASS_LOCATIONS) {
            inventory.setItem(index, glass);
        }
    }

    public Inventory getInventory() {
        return null;
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }

    @EventHandler
    public void onMoveInventoryItem(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }

        ItemStack itemOnCursor = e.getCursor();
        Player player = (Player) e.getWhoClicked();

        if (e.getRawSlot() != ITEM_SLOT || itemOnCursor == null) {
            if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                e.setCancelled(true);
            }
            return;
        }

        String cantDonate = plugin.getMessages().message("cantDonateItem");

        if (e.getAction().equals(InventoryAction.PLACE_ALL)) {
            if (eventHandler.tryAddItemsToQuest(itemOnCursor, player)) {
                player.setItemOnCursor(new ItemStack(Material.AIR));
            } else {

                player.sendMessage(cantDonate);
            }
        } else if (e.getAction().equals(InventoryAction.PLACE_ONE)) {
            if (eventHandler.tryAddItemsToQuest(new ItemStack(itemOnCursor.getType()), player)) {
                itemOnCursor.setAmount(itemOnCursor.getAmount() - 1);
                player.setItemOnCursor(itemOnCursor);
            } else {

                player.sendMessage(cantDonate);
            }
        } else {
            return;
        }
        e.setCancelled(true);
    }
}
