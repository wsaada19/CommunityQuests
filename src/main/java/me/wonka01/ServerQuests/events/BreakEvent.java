package me.wonka01.ServerQuests.events;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Setter;

import java.util.List;

public class BreakEvent extends QuestListener implements Listener {

    @Setter
    private static boolean disableDuplicateBreaks = true;

    private final String BROKEN = "BROKEN";
    private final MetadataValue meta = new FixedMetadataValue(JavaPlugin.getPlugin(ServerQuests.class), true);

    public BreakEvent(ActiveQuests activeQuests) {
        super(activeQuests);
        disableDuplicateBreaks = JavaPlugin.getPlugin(ServerQuests.class).getConfig()
                .getBoolean("disableDuplicateBreaks");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (disableDuplicateBreaks && event.getBlock().hasMetadata(BROKEN)) {
            return;
        }

        Block block = event.getBlock();

        if (event.isCancelled()) {
            return;
        }

        if (block.getBlockData() instanceof Ageable && !block.getType().equals(Material.FIRE)
                && !block.getType().equals(Material.SUGAR_CANE)
                && ((Ageable) block.getBlockData()).getAge() != ((Ageable) block.getBlockData())
                        .getMaximumAge()) {
            return;
        }

        List<QuestController> controllers = tryGetControllersOfObjectiveType(ObjectiveType.BLOCK_BREAK);

        block.setMetadata(BROKEN, meta);
        for (QuestController controller : controllers) {
            updateQuest(controller, event.getPlayer(), 1, ObjectiveType.BLOCK_BREAK, block.getType());
        }
    }
}
