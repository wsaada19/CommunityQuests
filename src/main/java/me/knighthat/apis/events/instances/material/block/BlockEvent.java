package me.knighthat.apis.events.instances.material.block;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.events.instances.material.MaterialEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

@Getter
public abstract class BlockEvent<T extends org.bukkit.event.block.BlockEvent> extends MaterialEvent<T> {

    private final @NonNull String state;
    private final @NonNull MetadataValue metadata;

    protected BlockEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType, @NonNull String state) {

        super(plugin, objectiveType);
        this.state = state;
        this.metadata = new FixedMetadataValue(plugin, true);
    }

    protected void attemptToUpdate(@NonNull org.bukkit.event.block.BlockEvent event, @NonNull HumanEntity breaker) {

        if (!event.getBlock().hasMetadata(state)) {

            Block block = event.getBlock();
            super.attemptToUpdate(breaker, block.getType());
            block.setMetadata(state, metadata);
        }
    }
}
