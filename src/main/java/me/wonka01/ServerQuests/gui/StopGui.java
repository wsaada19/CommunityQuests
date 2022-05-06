package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.util.NumberUtil;
import org.bukkit.Bukkit;
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

public class StopGui extends BaseGui implements InventoryHolder, Listener {

    private final ServerQuests plugin;
    private final Inventory inventory;

    public StopGui(ServerQuests plugin) {
        this.plugin = plugin;
        this.inventory = createInventory();
    }

    private @NonNull Inventory createInventory() {

        String title = plugin.getMessages().string("stopQuest");
        return Bukkit.createInventory(this, 36, title);
    }

    public void initializeItems() {
        inventory.clear();
        List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        int count = 0;
        for (QuestController controller : controllers) {
            String progress = NumberUtil.getNumberDisplay(controller.getQuestData().getAmountCompleted());
            int goal = controller.getQuestData().getQuestGoal();

            String progressString = plugin.getMessages().string("progress") + ": &a" + progress + "/" + goal;

            ItemStack item = createGuiItem(Material.DIAMOND,
                controller.getQuestData().getDisplayName(),
                controller.getQuestData().getDescription(),
                "",
                progressString,
                plugin.getMessages().string("endQuestText"));

            inventory.setItem(count, item);
            count++;
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

        for (QuestController controller : ActiveQuests.getActiveQuestsInstance().getActiveQuestsList()) {
            if (counter == slotNumber) {
                controllerToRemove = controller;
                break;
            }
            counter++;
        }

        if (controllerToRemove != null) {
            UUID id = controllerToRemove.getQuestId();
            ActiveQuests.getActiveQuestsInstance().endQuest(id);
            e.getWhoClicked().closeInventory();
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
