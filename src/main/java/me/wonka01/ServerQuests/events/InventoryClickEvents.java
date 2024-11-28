package me.wonka01.ServerQuests.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

public class InventoryClickEvents extends QuestListener implements Listener {

    private final ObjectiveType BREW_TYPE = ObjectiveType.BREW_POTION;

    public InventoryClickEvents(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        InventoryType inventoryType = event.getInventory().getType();

        if (inventoryType == InventoryType.BREWING) {
            Bukkit.getLogger().info("Brewing event " + event.getSlotType().toString());
            Bukkit.getLogger().info("Brewing action " + event.getAction().toString());
            if (event.getSlotType() == SlotType.CRAFTING
                    || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                List<QuestController> controllers = tryGetControllersOfObjectiveType(BREW_TYPE);

                Bukkit.getLogger().info("Brewing event " + item.getType().toString());
                for (QuestController controller : controllers) {
                    updateQuest(controller, player, 1, ObjectiveType.BREW_POTION, item.getType().toString(),
                            item.getItemMeta().getDisplayName());
                }
            }
        } else if (inventoryType == InventoryType.FURNACE
                || inventoryType.name().equals("BLAST_FURNACE")
                || inventoryType.name().equals("SMOKER")) {
            if (event.getSlotType() == SlotType.RESULT) {
                Bukkit.getLogger().info("Furnace inv type " + inventoryType.name());
                Bukkit.getLogger().info("Furnace item " + item.getType().toString());
                List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.SMELT_ITEM);
                for (QuestController controller : controllers) {
                    updateQuest(controller, player, 1, ObjectiveType.SMELT_ITEM, item.getType().toString(),
                            item.getItemMeta().getDisplayName());
                }
            }
        }
    }
}
