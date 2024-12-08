package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventPriority;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Setter;

import java.util.List;

public class PlaceEvent extends QuestListener implements Listener {

    @Setter
    private static boolean disableDuplicatePlaces;

    private final String PLACED = "PLACED";
    private final MetadataValue meta = new FixedMetadataValue(JavaPlugin.getPlugin(ServerQuests.class), true);

    public PlaceEvent(ActiveQuests activeQuests) {
        super(activeQuests);
        disableDuplicatePlaces = JavaPlugin.getPlugin(ServerQuests.class).getConfig()
                .getBoolean("disableDuplicatePlaces");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (disableDuplicatePlaces && block.hasMetadata(PLACED)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.BLOCK_PLACE);
        block.setMetadata(PLACED, meta);
        for (QuestController controller : controllers) {
            updateQuest(controller, event.getPlayer(), 1, ObjectiveType.BLOCK_PLACE, block.getType());
        }
    }
}
