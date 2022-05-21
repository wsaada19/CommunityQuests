package me.wonka01.ServerQuests.events.materials;

import me.knighthat.apis.events.MaterialEvent;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantEvent extends MaterialEvent<EnchantItemEvent> {

    public EnchantEvent(ServerQuests plugin) {
        super(plugin, ObjectiveType.ENCHANT_ITEM);
    }

    @Override
    @EventHandler
    public void event(EnchantItemEvent event) {
        super.attemptToUpdate(event.getEnchanter(), event.getItem().getType());
    }
}
