package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.events.questevents.GuiEvent;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class DonateQuestGui extends BaseGui implements InventoryHolder, Listener {
    private final int ITEM_SLOT = 22;
    private Inventory inventory;
    private int[] GLASS_LOCATIONS = {12, 13, 14, 21, 23, 30, 31, 32};
    private GuiEvent eventHandler;

    public DonateQuestGui() {
        inventory = Bukkit.createInventory(this, 45,  ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getDonateMenu()));
        initializeItems();
        eventHandler = new GuiEvent(ActiveQuests.getActiveQuestsInstance());
    }

    public void initializeItems() {
        ItemStack glass = createGuiItem(Material.GREEN_STAINED_GLASS_PANE, " ", "");
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
            return;
        }

        if (e.getAction().equals(InventoryAction.PLACE_ALL)) {
            if (eventHandler.tryAddItemsToQuest(itemOnCursor, player)) {
                player.setItemOnCursor(new ItemStack(Material.AIR));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getCantDonate()));
            }
        } else if (e.getAction().equals(InventoryAction.PLACE_ONE)) {
            if (eventHandler.tryAddItemsToQuest(new ItemStack(itemOnCursor.getType()), player)) {
                itemOnCursor.setAmount(itemOnCursor.getAmount() - 1);
                player.setItemOnCursor(itemOnCursor);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getCantDonate()));
            }
        } else {
            return;
        }
        e.setCancelled(true);
    }
}
