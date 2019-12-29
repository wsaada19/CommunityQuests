package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.handlers.EventTypeHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EventTypeGui extends BaseGui implements Listener, InventoryHolder {

    private final String COMPETITIVE = "COMPETITIVE";
    private final String COOPERATIVE = "COOPERATIVE";

    private Inventory inventory;
    private QuestModel model;

    public EventTypeGui() {
        inventory = Bukkit.createInventory(this, 27, ChatColor.YELLOW  + "" +  ChatColor.BOLD +  "SELECT AN EVENT TYPE");
    }

    @Override
    public void initializeItems() {
        inventory.setItem(12, createGuiItem(Material.PLAYER_HEAD, COOPERATIVE,
                "Sever wide cooperative quest."));
        inventory.setItem(13, createGuiItem(Material.DIAMOND_SWORD, COMPETITIVE,
                "See who can finish first!"));
        inventory.setItem(8, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.RED + "BACK",
                "Return to the event list"));
    }

    public void openInventory(Player p, QuestModel model) {
        p.openInventory(inventory);
        this.model = model;
    }

    public Inventory getInventory() {
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(!clickEventCheck(e, this)){
            return;
        }
        Player player = (Player)e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(COOPERATIVE)){
            ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(model, EventTypeHandler.EventType.COLLAB);
        } else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(COMPETITIVE)) {
            ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(model, EventTypeHandler.EventType.COMPETITIVE);
        } else if(e.getRawSlot() == 8){
            player.closeInventory();
            JavaPlugin.getPlugin(ServerQuests.class).getStartGui().openInventory(player);
            return;
        } else {
            return;
        }
        player.closeInventory();
    }
}
