package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearEvent extends EntityEvent<PlayerShearEntityEvent> {

    public ShearEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.SHEAR);
    }

    @Override
    @EventHandler
    public void event(PlayerShearEntityEvent event) {
        super.update(event.getPlayer());
    }
}
