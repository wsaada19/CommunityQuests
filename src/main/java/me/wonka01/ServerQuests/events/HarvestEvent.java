package me.wonka01.ServerQuests.events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

public class HarvestEvent extends QuestListener implements Listener {

    public HarvestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHarvestEvent(PlayerHarvestBlockEvent event) {
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.HARVEST);
        for (QuestController controller : controllers) {
            List<ItemStack> harvestedItems = event.getItemsHarvested();
            for (ItemStack item : harvestedItems) {
                updateQuest(controller, event.getPlayer(), item.getAmount(), ObjectiveType.HARVEST, item.getType());
            }
        }
    }
}
