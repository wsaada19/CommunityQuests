package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MilkingEvent extends EntityEvent<PlayerInteractAtEntityEvent> {

    public MilkingEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.MILK_COW);
    }

    @Override
    @EventHandler
    public void event(PlayerInteractAtEntityEvent event) {


        if (!(event.getRightClicked() instanceof Cow &&
            event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BUCKET)))
            return;

        super.attemptToUpdate(event.getPlayer(), event.getRightClicked().getType());
    }
}
