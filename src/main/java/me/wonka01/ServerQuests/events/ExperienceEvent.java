package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.List;

public class ExperienceEvent extends QuestListener implements Listener {

    public ExperienceEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExperienceEvent(PlayerExpChangeEvent event) {
        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.EXPERIENCE);
        for (QuestController controller : controllers) {
            updateQuest(controller, event.getPlayer(), event.getAmount(), ObjectiveType.EXPERIENCE);
        }
    }
}
