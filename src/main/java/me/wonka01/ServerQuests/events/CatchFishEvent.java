package me.wonka01.ServerQuests.events;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.List;

public class CatchFishEvent extends QuestListener implements Listener {

    public CatchFishEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onCatchFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            List<QuestController> controllers = tryGetControllersOfEventType(ObjectiveType.CATCH_FISH);
            String fishName = event.getCaught().getName();

            for (QuestController controller : controllers) {
                List<String> entities = controller.getEventConstraints().getMobNames();
                if(entities.isEmpty() || Utils.contains(entities, fishName))
                    updateQuest(controller, event.getPlayer(), 1);
            }
        }
    }
}
