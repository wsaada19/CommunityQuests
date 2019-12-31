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

public class TypeGui extends BaseGui implements Listener, InventoryHolder {

    private final String COMPETITIVE = ChatColor.GREEN + "Competitive";
    private final String COOPERATIVE = ChatColor.GREEN + "Cooperative";
    private final String COMP_DESCRIPTION = ChatColor.WHITE + "See who can finish first!";
    private final String COOP_DESCRIPTION = ChatColor.WHITE + "Sever wide cooperative quest.";

    private Inventory inventory;
    private QuestModel model;

    public TypeGui() {
        inventory = Bukkit.createInventory(this, 27, ChatColor.WHITE  + "" +  ChatColor.BOLD +  "Select an Event Type");
    }

    @Override
    public void initializeItems() {
        inventory.setItem(12, createGuiItem(Material.PLAYER_HEAD, COOPERATIVE,
                COOP_DESCRIPTION));
        inventory.setItem(13, createGuiItem(Material.DIAMOND_SWORD, COMPETITIVE,
                COOP_DESCRIPTION));
        inventory.setItem(8, createGuiItem(Material.ARROW, ChatColor.GREEN + "Go Back",
                ChatColor.GRAY + "Go back to the event list"));
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
