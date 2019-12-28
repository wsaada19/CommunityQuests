package me.wonka01.ServerQuests.gui;

import com.sun.istack.internal.NotNull;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.QuestModel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class StartEventGui extends BaseGui implements InventoryHolder, Listener {

    private Inventory inventory;
    private QuestLibrary questLibrary;
    private EventTypeGui eventTypeGui;

    public StartEventGui(EventTypeGui eventTypeGui) {
        inventory = Bukkit.createInventory(this, 9, "Begin Server Event");
        questLibrary = QuestLibrary.getQuestLibraryInstance();
        this.eventTypeGui = eventTypeGui;
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {

        Set<String> keys = QuestLibrary.getQuestLibraryInstance().getAllQuestKeys();

        for(String key : keys)
        {
            QuestModel model = questLibrary.getQuestModelById(key);
            inventory.addItem(createGuiItem(Material.DIAMOND_SWORD, model.getQuestId() , model.getDisplayName(), model.getEventDescription()));
        }
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(!clickEventCheck(e, this)){
            return;
        }
        Player player = (Player)e.getWhoClicked();

        ItemStack clickedItem = e.getCurrentItem();

        QuestModel model = questLibrary.getQuestModelById(clickedItem.getItemMeta().getDisplayName());
        player.closeInventory();
        eventTypeGui.openInventory(player, model);
    }
}
