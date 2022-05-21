package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCInteractEvent extends EntityEvent<PlayerInteractAtEntityEvent> {

    public NPCInteractEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.MOB_KILL);
    }

    @Override
    @EventHandler
    public void event(PlayerInteractAtEntityEvent event) {

    }
}
