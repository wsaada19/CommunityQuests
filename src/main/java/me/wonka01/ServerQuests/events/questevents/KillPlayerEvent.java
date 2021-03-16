package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class KillPlayerEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.PLAYER_KILL;

    public KillPlayerEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onKillPlayer(PlayerDeathEvent event) {

        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            updateQuest(controller, killer, 1);
        }
    }
}
