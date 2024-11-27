package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;

public class MilkCowEvent extends QuestListener implements Listener {

    public MilkCowEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMilkCow(PlayerBucketFillEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (event.getItemStack() != null && event.getItemStack().getType() == Material.MILK_BUCKET) {
            List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.MILK_COW);

            for (QuestController controller : controllers) {
                updateQuest(controller, player, 1, ObjectiveType.MILK_COW);
            }
        }
    }
}
