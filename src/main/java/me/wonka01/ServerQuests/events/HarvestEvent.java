package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HarvestEvent extends QuestListener implements Listener {

    public HarvestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onHarvestEvent(PlayerHarvestBlockEvent event) {
        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.HARVEST);
        for (QuestController controller : controllers) {
            List<Material> materials = controller.getEventConstraints().getMaterials();

            List<ItemStack> harvestedItems = event.getItemsHarvested();
            for (ItemStack item : harvestedItems) {
                if (materials.isEmpty() || materials.contains(item.getType())) {
                    updateQuest(controller, event.getPlayer(), item.getAmount());
                }
            }
        }
    }
}
