package me.wonka01.ServerQuests.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.knighthat.apis.utils.Utils;
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
            if (event.getSlotType() == SlotType.CRAFTING
                    || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                List<QuestController> controllers = tryGetControllersOfObjectiveType(BREW_TYPE);

                Bukkit.getLogger().info("Brewing item " + item.getType().toString());
                PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

                if (potionMeta == null) {
                    Bukkit.getLogger().info("PotionMeta is null for item " + item.getType().toString());
                    return;
                }

                Bukkit.getLogger()
                        .info("PotionMeta is not null for item " + potionMeta.getBasePotionData().getType().toString());

                for (QuestController controller : controllers) {
                    updateQuest(controller, player, 1, ObjectiveType.BREW_POTION, item);
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
