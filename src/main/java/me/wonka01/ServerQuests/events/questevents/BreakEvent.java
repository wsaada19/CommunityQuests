package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Iterator;

public class BreakEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.BLOCK_BREAK;

    public BreakEvent(ActiveQuests activeQuests)
    {
        super(activeQuests);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        String blockName = event.getBlock().getType().toString();
        Iterator<QuestController> iterator = activeQuests.getActiveQuestsList().iterator();
        while (iterator.hasNext()){
            QuestController controller = iterator.next();

            if(!controller.getListenerType().equals(TYPE)){continue;}

            for(String blockType : controller.getEventConstraints().getMaterialNames())
            {
                if(blockName.equalsIgnoreCase(blockType)) {
                    updateQuest(controller, event.getPlayer(), 1);
                    break;
                }
            }
        }
        removedFinishedQuests();
    }
}
