package me.wonka01.ServerQuests.events.materials;

import me.knighthat.apis.events.MaterialEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeEvent extends MaterialEvent<PlayerItemConsumeEvent> {

    public ConsumeEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.CONSUME_ITEM);
    }

    @Override
    @EventHandler
    public void event(PlayerItemConsumeEvent event) {
        super.attemptToUpdate(event.getPlayer(), event.getItem().getType());
    }
}
