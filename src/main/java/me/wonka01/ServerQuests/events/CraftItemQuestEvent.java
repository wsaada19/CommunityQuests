package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CraftItemQuestEvent extends QuestListener implements Listener {

    public CraftItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCraftItem(CraftItemEvent event) {
        ItemStack craftedItem = event.getInventory().getResult(); // Get result of recipe
        if (event.isCancelled() || craftedItem == null) {
            return;
        }

        Inventory Inventory = event.getInventory(); // Get crafting inventory
        ClickType clickType = event.getClick();
        Material material = craftedItem.getType();
        Material cursorMaterial = event.getCursor().getType();
        int realAmount = craftedItem.getAmount();

        if (clickType.isShiftClick()) {
            int lowerAmount = craftedItem.getMaxStackSize() + 1000; // Set lower at recipe result max stack size + 1000
                                                                    // (or just highter max stacksize of reciped item)
            for (ItemStack actualItem : Inventory.getContents()) // For each item in crafting inventory
            {
                if (!actualItem.getType().isAir() && lowerAmount > actualItem.getAmount()
                        && !actualItem.getType().equals(craftedItem.getType())) // if slot is not air && lowerAmount is
                                                                                // highter than this slot amount && it's
                                                                                // not the recipe amount
                    lowerAmount = actualItem.getAmount(); // Set new lower amount
            }
            // Calculate the final amount : lowerAmount * craftedItem.getAmount
            realAmount = lowerAmount * craftedItem.getAmount();
        } else if (!cursorMaterial.isAir() && !cursorMaterial.equals(material)) {
            return;
        } else if (cursorMaterial.equals(material)
                && cursorMaterial.getMaxStackSize() < event.getCursor().getAmount() + realAmount) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.CRAFT_ITEM);
        for (QuestController controller : controllers) {
            updateQuest(controller, (Player) event.getWhoClicked(), realAmount, ObjectiveType.CRAFT_ITEM, material);
        }
    }
}
