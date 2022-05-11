package me.knighthat.apis.events.instances;

import lombok.NonNull;
import me.knighthat.apis.events.instances.entity.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.entity.EntityTameEvent;

public class TameEvent extends EntityEvent<EntityTameEvent> {

    public TameEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {
        super(plugin, objectiveType);
    }

    @Override
    public void event(EntityTameEvent event) {

    }
}
