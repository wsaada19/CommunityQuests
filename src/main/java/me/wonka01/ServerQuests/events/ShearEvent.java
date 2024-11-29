package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class ShearEvent extends QuestListener implements Listener {

    public ShearEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSheer(PlayerShearEntityEvent shearEvent) {
        shearEvent.getEntity();
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.SHEAR);
        for (QuestController controller : controllers) {
            updateQuest(controller, shearEvent.getPlayer(), 1, ObjectiveType.SHEAR,
                    shearEvent.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerUseShearsOnPumpkin(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.PUMPKIN) {
            return;
        }

        ItemStack itemInHand = event.getItem();
        if (itemInHand == null || itemInHand.getType() != Material.SHEARS) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.CARVE_PUMPKIN);
        for (QuestController controller : controllers) {
            updateQuest(controller, event.getPlayer(), 1, ObjectiveType.CARVE_PUMPKIN);
        }
    }

}
