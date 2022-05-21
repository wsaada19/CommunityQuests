package me.wonka01.ServerQuests.events.materials.blocks;

import me.knighthat.apis.events.BlockEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent extends BlockEvent<BlockPlaceEvent> {

    public PlaceEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.BLOCK_PLACE, "PLACED");
    }

    @Override
    @EventHandler
    public void event(BlockPlaceEvent event) {
        super.attemptToUpdate(event, event.getPlayer());
    }
}
