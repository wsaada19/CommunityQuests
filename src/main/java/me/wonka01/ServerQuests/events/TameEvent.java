package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

import java.util.List;

public class TameEvent extends QuestListener implements Listener {
    public TameEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onTameEvent(EntityTameEvent tameEvent) {
        if (tameEvent.getOwner() instanceof Player) {
            List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.TAME);
            for (QuestController controller : controllers) {
                updateQuest(controller, (Player) tameEvent.getOwner(), 1, ObjectiveType.TAME,
                        tameEvent.getEntity().getType().toString(), tameEvent.getEntity().getName());
            }
        }
    }
}