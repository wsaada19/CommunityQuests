package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.List;

public class CatchFishEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.CATCH_FISH;

    public CatchFishEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    //TODO Add event constraints for fish types like MobKillEvent
    @EventHandler
    public void onCatchFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
            for (QuestController controller : controllers) {
                updateQuest(controller, event.getPlayer(), 1);
            }
        }
    }
}
