package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SelectDonateQuestMenu extends Menu {

    protected SelectDonateQuestMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "donateMenu", 36);
    }

    @Override
    protected void setContents() {

        for (QuestController ctrl : super.getControllers())
            if (ctrl.getObjective().equals(ObjectiveType.GUI)) {

                QuestData data = ctrl.getQuestData();

                List<String> lore = getLoreFromData(data);
                lore.add("&eClick here to donate to this quest");

                ItemStack item = super.createItemStack(Material.DIAMOND, data.getDisplayName(), lore);
                getInventory().addItem(item);
            }
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        try {
            int slot = event.getRawSlot();
            if (super.getControllers().get(slot) != null)
                new DonateMenu(getPlugin(), getOwner()).open();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
