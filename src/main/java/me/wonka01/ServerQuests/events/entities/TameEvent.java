package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

public class TameEvent extends EntityEvent<EntityTameEvent> {

    public TameEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.TAME);
    }

    @Override
    @EventHandler
    public void event(EntityTameEvent event) {
        super.attemptToUpdate((HumanEntity) event.getOwner(), event.getEntity().getType());
    }
}
