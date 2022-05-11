package me.knighthat.apis.events.instances.entity;

import me.knighthat.apis.events.EventBase;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.List;

public class CatchFishEvent extends EventBase<PlayerFishEvent> {

    public CatchFishEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.CATCH_FISH);
    }

    @Override
    @EventHandler
    public void event(PlayerFishEvent event) {

        if (event.getCaught() == null || !event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))
            return;

        for (QuestController ctrl : super.getControllers()) {

            List<String> entities = ctrl.getEventConstraints().getMobNames();
            if (entities.isEmpty() || Utils.contains(entities, event.getCaught().getType()))
                super.update(ctrl, event.getPlayer(), 1);
        }
    }
}
