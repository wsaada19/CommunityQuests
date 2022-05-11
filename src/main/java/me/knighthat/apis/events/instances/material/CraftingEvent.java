package me.knighthat.apis.events.instances.material;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingEvent extends MaterialEvent<CraftItemEvent> {

    public CraftingEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.CRAFT_ITEM);
    }

    @Override
    @EventHandler
    public void event(CraftItemEvent event) {

        int resultAmount = event.getRecipe().getResult().getAmount();
        Material resultType = event.getRecipe().getResult().getType();

        super.attemptToUpdate(event.getWhoClicked(), resultType, resultAmount);
    }
}
