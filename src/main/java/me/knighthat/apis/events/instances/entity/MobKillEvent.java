package me.knighthat.apis.events.instances.entity;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKillEvent extends EntityEvent<EntityDeathEvent> {

    public MobKillEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.MOB_KILL);
    }

    @Override
    public void event(EntityDeathEvent event) {

        Player killer = event.getEntity().getKiller();
        if (killer == null)
            return;

        super.attemptToUpdate(killer, event.getEntityType());
    }
}
