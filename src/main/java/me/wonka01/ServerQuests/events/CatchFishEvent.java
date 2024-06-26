package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.List;

public class CatchFishEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.CATCH_FISH;

    public CatchFishEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onCatchFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            List<QuestController> controllers = tryGetControllersOfObjectiveType(TYPE);
            Item item = (Item) event.getCaught();
            String fishName = item.getItemStack().getType().toString();

            for (QuestController controller : controllers) {
                updateQuest(controller, event.getPlayer(), 1, ObjectiveType.CATCH_FISH, fishName, item.getName());
            }
        }
    }
}
