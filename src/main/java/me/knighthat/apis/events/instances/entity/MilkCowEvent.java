package me.knighthat.apis.events.instances.entity;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MilkCowEvent extends EntityEvent<PlayerInteractAtEntityEvent> {

    public MilkCowEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.MILK_COW);
    }

    @Override
    public void event(PlayerInteractAtEntityEvent event) {

        if (!(event.getRightClicked() instanceof Cow &&
            event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BUCKET)))
            return;

        super.attemptToUpdate(event.getPlayer(), event.getRightClicked().getType());
    }
}
