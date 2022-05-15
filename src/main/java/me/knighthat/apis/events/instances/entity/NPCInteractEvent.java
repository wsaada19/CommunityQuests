package me.knighthat.apis.events.instances.entity;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCInteractEvent extends EntityEvent<PlayerInteractAtEntityEvent> {

    public NPCInteractEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {
        super(plugin, objectiveType);
    }

    @Override
    public void event(PlayerInteractAtEntityEvent event) {

    }
}
