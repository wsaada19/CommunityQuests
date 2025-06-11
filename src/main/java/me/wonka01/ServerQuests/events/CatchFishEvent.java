package me.wonka01.ServerQuests.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

public class CatchFishEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.CATCH_FISH;

    public CatchFishEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onCatchFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Item item = (Item) event.getCaught();
            String fishName = item.getItemStack().getType().toString();

            // log fish name and type
            Bukkit.getLogger().info("Caught fish: " + fishName);
            Bukkit.getLogger().info("Fish type: " + item.getName());

            List<QuestController> controllers = tryGetControllersOfObjectiveType(TYPE);
            for (QuestController controller : controllers) {
                updateQuest(controller, event.getPlayer(), 1, ObjectiveType.CATCH_FISH, fishName);
            }
        }
    }
}
