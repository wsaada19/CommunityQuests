package me.knighthat.apis.events.instances.entity;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillPlayerEvent extends EntityEvent<PlayerDeathEvent> {

    public KillPlayerEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.PLAYER_KILL);
    }

    @Override
    public void event(PlayerDeathEvent event) {

        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        super.attemptToUpdate(killer, event.getEntity().getType());
    }
}
