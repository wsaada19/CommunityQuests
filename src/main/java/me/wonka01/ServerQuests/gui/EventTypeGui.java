package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.handlers.EventTypeHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class EventTypeGui extends BaseGui implements Listener, InventoryHolder {

    private final String COMPETITIVE = "COMPETITIVE";
    private final String COOPERATIVE = "COOPERATIVE";

    private Inventory inventory;
    private QuestModel model;

    public EventTypeGui() {
        inventory = Bukkit.createInventory(this, 9, "Select the Event Type");
    }

    @Override
    public void initializeItems() {
        inventory.addItem(createGuiItem(Material.DIAMOND_SWORD, COOPERATIVE,
                "In cooperative events all players work together to achieve a common goal."));
        inventory.addItem(createGuiItem(Material.DIAMOND_SWORD, COMPETITIVE,
                "In competitive events, players go against each other to see who can reach a goal first."));
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
        } else {
            ActiveQuests.getActiveQuestsInstance().InitializeQuestListener(model, EventTypeHandler.EventType.COMPETITIVE);
        }
        player.closeInventory();
    }
}
