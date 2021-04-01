package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class PlaceEvent extends QuestListener implements Listener {

    private final ObjectiveType TYPE = ObjectiveType.BLOCK_PLACE;
    private final String PLACED = "PLACED";
    private final MetadataValue meta = new FixedMetadataValue(JavaPlugin.getPlugin(ServerQuests.class), true);

    public PlaceEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.hasMetadata(PLACED)) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfEventType(TYPE);
        for (QuestController controller : controllers) {
            List<String> materials = controller.getQuestConstraints().getMaterialNames();
            if (materials.isEmpty() || containsMaterial(block.getType().toString(), materials)) {
                updateQuest(controller, event.getPlayer(), 1);
                block.setMetadata(PLACED, meta);
            }
        }
    }

    private boolean containsMaterial(String material, List<String> materials) {
        for (String targetMaterial : materials) {
            if (material.contains(targetMaterial.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
