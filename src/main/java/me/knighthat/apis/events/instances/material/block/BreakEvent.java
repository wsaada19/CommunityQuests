package me.knighthat.apis.events.instances.material.block;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent extends BlockEvent<BlockBreakEvent> {


    public BreakEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.BLOCK_BREAK, "BROKEN");
    }

    @Override
    @EventHandler
    public void event(BlockBreakEvent event) {
        super.attemptToUpdate(event, event.getPlayer());
    }
}
