package me.wonka01.ServerQuests.gui;

import com.sun.istack.internal.NotNull;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.handlers.EventListenerHandler;
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
    private int startingSlot;

    public StartEventGui(EventTypeGui eventTypeGui) {
        inventory = Bukkit.createInventory(this, 27, "Begin Server Event");
        questLibrary = QuestLibrary.getQuestLibraryInstance();
        this.eventTypeGui = eventTypeGui;
        startingSlot = 1;
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {

        Set<String> keys = QuestLibrary.getQuestLibraryInstance().getAllQuestKeys();
        int count = startingSlot;
        for(String key : keys)
        {
            QuestModel model = questLibrary.getQuestModelById(key);
            Material material = EventListenerHandler.getEventTypeMaterial(model.getEventType());

            inventory.setItem(count, createGuiItem(material, model.getQuestId() , model.getDisplayName(), model.getEventDescription()));
            count++;
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
        int clickedSlot = e.getRawSlot();
        QuestModel model = null;
        int count = startingSlot;

        for(String modelKeys : questLibrary.getAllQuestKeys()){
            if(clickedSlot == count){
                model = questLibrary.getQuestModelById(modelKeys);
                break;
            }
        }

        if(model == null){
            return;
        }
        player.closeInventory();
        eventTypeGui.openInventory(player, model);
    }
}
