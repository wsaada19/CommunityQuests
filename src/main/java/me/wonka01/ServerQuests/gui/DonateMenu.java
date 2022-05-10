package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.events.GuiEvent;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DonateMenu extends Menu {

    private final int inputSlot = 22;

    public DonateMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "donateMenu", 45);
    }

    @Override
    protected void setContents() {

        ItemStack item = super.createItemStack(Material.DIAMOND_BLOCK, " ");

        for (int slot = 0; slot < getSlots(); slot++)
            if (slot != inputSlot)
                getInventory().setItem(slot, item);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        String cantDonateMessage = getPlugin().messages().message("cantDonateItem");
        GuiEvent handler = new GuiEvent(ActiveQuests.getActiveQuestsInstance());
        ItemStack atCursor = event.getCursor().clone();

        switch (event.getAction()) {

            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:

                if (event.getClickedInventory() != null)
                    if (!event.getClickedInventory().equals(getInventory()))
                        event.setCancelled(false);
                break;

            case PLACE_ALL:

                if (event.getCursor() != null)
                    if (handler.tryAddItemsToQuest(atCursor, getOwner())) {

                        getOwner().setItemOnCursor(new ItemStack(Material.AIR));
                    } else {

                        getOwner().sendMessage(cantDonateMessage);
                    }
                break;

            case PLACE_ONE:

                if (event.getCursor() != null) {
                    atCursor.setAmount(atCursor.getAmount() - 1);
                    if (handler.tryAddItemsToQuest(atCursor, getOwner())) {
                        getOwner().setItemOnCursor(atCursor);
                    } else {
                        getOwner().sendMessage(cantDonateMessage);
                    }
                }
            default:
                break;
        }

    }
}
