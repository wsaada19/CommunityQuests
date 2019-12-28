package me.wonka01.ServerQuests.gui;

import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
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

import java.util.List;
import java.util.UUID;

public class StopEventGui extends BaseGui implements InventoryHolder, Listener {
    private Inventory inventory;

    public StopEventGui() {
        inventory = Bukkit.createInventory(this, 36, "Active Quests");
    }

    public void initializeItems() {
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();

        for(QuestController controller : controllers){
            int progress = controller.getQuestData().getAmountCompleted();
            int goal = controller.getQuestData().getQuestGoal();

            String progressString = ChatColor.GRAY + "Progress: " + ChatColor.GREEN + progress + "/" + goal;

            ItemStack item = createGuiItem(Material.DIAMOND,
                    ChatColor.YELLOW + controller.getQuestData().getDisplayName(),
                    ChatColor.WHITE + controller.getQuestData().getDescription(),
                    "",
                    progressString);

            inventory.addItem(item);
        }
    }

    public void openInventory(Player p) {
        p.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!clickEventCheck(e, this)) {
            return;
        }

        int slotNumber = e.getRawSlot();
        int counter = 0;
        QuestController controllerToRemove = null;
        Bukkit.getServer().broadcastMessage("Slot: " + slotNumber);

        for(QuestController controller : ActiveQuests.getActiveQuestsInstance().getActiveQuestsList()){
            if(counter == slotNumber){
                controllerToRemove = controller;
                break;
            }
        }

        if(controllerToRemove != null){
            UUID id = controllerToRemove.getQuestId();
            ActiveQuests.getActiveQuestsInstance().endQuest(id);
        }

    }

    public Inventory getInventory() {
        return inventory;
    }
}
