package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class PlaceEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.BLOCK_PLACE;
    private final String PLACED = "PLACED";
    private final MetadataValue meta = new FixedMetadataValue(JavaPlugin.getPlugin(ServerQuests.class), true);

    public PlaceEvent(ActiveQuests activeQuests) {
        super(activeQuests);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Bukkit.getServer().broadcastMessage("DEBUG: Placed a " + event.getBlock().getType().toString());
        Block block = event.getBlock();
        if(block.hasMetadata(PLACED)){
            return;
        }
        for (QuestController controller : activeQuests.getActiveQuestsList()){
            if(!controller.getListenerType().equals(TYPE)){continue;}
            List<String> materials = controller.getEventConstraints().getMaterialNames();

            if(materials.isEmpty() || containsMaterial(block.getType().toString(), materials)){
                updateQuest(controller, event.getPlayer(), 1);
                block.setMetadata(PLACED, meta);
            }
        }
        removedFinishedQuests();
    }

    private boolean containsMaterial(String material, List<String> materials){
        for(String targetMaterial : materials){
            if(targetMaterial.toUpperCase().contains(material)){
                return true;
            }
        }
        return false;
    }
}
