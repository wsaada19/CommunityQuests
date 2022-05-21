package me.wonka01.ServerQuests.events.entities;

import me.knighthat.apis.events.EntityEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class CatchFishEvent extends EntityEvent<PlayerFishEvent> {

    public CatchFishEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.CATCH_FISH);
    }

    @Override
    @EventHandler
    public void event(PlayerFishEvent event) {

        if (event.getCaught() == null || !event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))
            return;

        Item caught = (Item) event.getCaught();
        ItemStack converted = caught.getItemStack();

        super.attemptToUpdate(event.getPlayer(), converted.getType());
    }
}
