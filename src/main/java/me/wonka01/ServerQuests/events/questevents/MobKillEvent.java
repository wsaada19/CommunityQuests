package me.wonka01.ServerQuests.events.questevents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class MobKillEvent extends QuestListener implements Listener {

    private final EventListenerHandler.EventListenerType TYPE = EventListenerHandler.EventListenerType.MOB_Kill;

    public MobKillEvent(ActiveQuests activeQuests)
    {
        super(activeQuests);
    }

    @EventHandler
    public void OnPlayerKillMob(EntityDeathEvent event)
    {

        Entity killer = event.getEntity().getKiller();
        if(!(killer instanceof Player)) { return; }

        String mobName = event.getEntity().getName();

        for(QuestController questController : activeQuests.getActiveQuestsList())
        {

            if(!questController.getListenerType().equals(TYPE)){continue;}

            List<String> mobTypes = questController.getEventConstraints().getMobNames();

            if(mobTypes.isEmpty() || mobTypes.contains(mobName)){
                Bukkit.getServer().broadcastMessage("DEBUG: Player killed an entity " + mobName);
                updateQuest(questController, (Player)killer, 1);
            }
        }
        removedFinishedQuests();
    }
}
