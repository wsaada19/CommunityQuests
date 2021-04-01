package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
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

    private final int COOP_SLOT = 12;
    private final int COMP_SLOT = 14;
    private final int BACK_SLOT = 18;

    private Inventory inventory;
    private QuestModel model;

    public TypeGui() {
        inventory = Bukkit.createInventory(this, 27, "Select an Event Type");
    }

    @Override
    public void initializeItems() {
        inventory.setItem(COOP_SLOT, createGuiItem(Material.PLAYER_HEAD, COOPERATIVE));
        inventory.setItem(COMP_SLOT, createGuiItem(Material.DIAMOND_SWORD, COMPETITIVE));
        inventory.setItem(BACK_SLOT, createGuiItem(Material.ARROW, ChatColor.GREEN + "Go Back",
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

        if (!clickEventCheck(e, this)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(COOPERATIVE)) {
            ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, EventType.COLLAB);
        } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(COMPETITIVE)) {
            ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, EventType.COMPETITIVE);
        } else if (e.getRawSlot() == BACK_SLOT) {
            player.closeInventory();
            JavaPlugin.getPlugin(ServerQuests.class).getStartGui().openInventory(player);
            return;
        } else {
            return;
        }
        player.closeInventory();
    }
}
