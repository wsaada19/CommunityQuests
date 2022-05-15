package me.knighthat.apis.events.instances;

import lombok.NonNull;
import me.knighthat.apis.events.EventBase;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantEvent extends EventBase<EnchantItemEvent> {

    public EnchantEvent(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {
        super(plugin, objectiveType);
    }

    @Override
    public void event(EnchantItemEvent event) {

    }
}
