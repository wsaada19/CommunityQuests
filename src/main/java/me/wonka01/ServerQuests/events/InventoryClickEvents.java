package me.wonka01.ServerQuests.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

public class InventoryClickEvents extends QuestListener implements Listener {

    private final ObjectiveType BREW_TYPE = ObjectiveType.BREW_POTION;
    private ServerQuests plugin;

    public InventoryClickEvents(ActiveQuests activeQuests, ServerQuests plugin) {
        super(activeQuests);
        this.plugin = plugin;
    }

    private void markItemAsProcessed(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey uniqueKey = new NamespacedKey(plugin, "processed");
        container.set(uniqueKey, PersistentDataType.STRING, UUID.randomUUID().toString());
        item.setItemMeta(meta);
    }

    private boolean isItemProcessed(ItemStack item) {
        if (item == null || !item.hasItemMeta())
            return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey uniqueKey = new NamespacedKey(plugin, "processed");

        return container.has(uniqueKey, PersistentDataType.STRING);
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
            if (event.getSlotType() == SlotType.CRAFTING
                    || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                List<QuestController> controllers = tryGetControllersOfObjectiveType(BREW_TYPE);

                PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

                // Make sure potion hasn't already been used for a community quest
                if (isItemProcessed(item)) {
                    return;
                }

                markItemAsProcessed(item);
                for (QuestController controller : controllers) {
                    updateQuest(controller, player, 1, ObjectiveType.BREW_POTION,
                            potionMeta.getBasePotionData().getType());
                }
            }
        } else if (inventoryType == InventoryType.FURNACE
                || inventoryType.name().equals("BLAST_FURNACE")
                || inventoryType.name().equals("SMOKER")) {
            if (event.getSlotType() == SlotType.RESULT) {
                List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.SMELT_ITEM);
                for (QuestController controller : controllers) {
                    updateQuest(controller, player, item.getAmount(), ObjectiveType.SMELT_ITEM,
                            item);
                }
            }
        }
    }
}
