package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class BreakEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.BLOCK_BREAK;
    private final String BROKEN = "BROKEN";
    private final MetadataValue meta = new FixedMetadataValue(JavaPlugin.getPlugin(ServerQuests.class), true);


    public BreakEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (event.getBlock().hasMetadata(BROKEN)) {
            return;
        }

        String blockName = event.getBlock().getType().toString();

        Iterator<QuestController> iterator = activeQuests.getActiveQuestsList().iterator();
        while (iterator.hasNext()) {
            QuestController controller = iterator.next();

            if (!controller.getListenerType().equals(TYPE)) {
                continue;
            }

            for (String blockType : controller.getEventConstraints().getMaterialNames()) {
                if (blockName.equalsIgnoreCase(blockType)) {
                    event.getBlock().setMetadata(BROKEN, meta);
                    updateQuest(controller, event.getPlayer(), 1);
                    break;
                }
            }
        }
        removedFinishedQuests();
    }
}
