package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;

public class ConsumeItemQuestEvent extends QuestListener implements Listener {

    public ConsumeItemQuestEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.CONSUME_ITEM);
        for (QuestController controller : controllers) {
            updateQuest(controller, player, 1, ObjectiveType.CONSUME_ITEM, event.getItem().getType());
        }
    }
}
