package me.knighthat.apis.events;

import lombok.NonNull;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Range;

import java.util.List;

public abstract class EntityEvent<T extends Event> extends EventBase<T> {

    protected EntityEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {
        super(plugin, objectiveType);
    }

    protected <V> boolean attemptToUpdate(@NonNull HumanEntity player, @NonNull V value) {
        return attemptToUpdate(player, value, 1);
    }

    protected <V> boolean attemptToUpdate(@NonNull HumanEntity player, @NonNull V value,
                                          @Range(from = 0, to = Long.MAX_VALUE) int amount) {

        for (QuestController ctrl : super.getControllers()) {

            List<String> entities = ctrl.getEventConstraints().getMobNames();
            if (entities.isEmpty() || Utils.contains(entities, value)) {

                super.update(ctrl, (Player) player, amount);
                return true;
            }
        }

        return false;
    }
}
