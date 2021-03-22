package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;

public class MilkCowEvent extends QuestListener implements Listener {

    public MilkCowEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onPlayerMilkCow(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity cow = event.getRightClicked();
        if (!(cow instanceof Cow) || !(player.getInventory().getItemInMainHand().getType().equals(Material.BUCKET))) {
            return;
        }
        List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.MILK_COW);
        for (QuestController controller : controllers) {
            updateQuest(controller, player, 1);
        }
    }
}
