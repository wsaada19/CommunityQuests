package me.wonka01.ServerQuests.gui;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DonateMenu extends Menu {

    @Getter
    private final int inputSlot = 22;
    private @NonNull Material borderItem;

    public DonateMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "donateMenu", 45);

        borderItem = Material.getMaterial(getPlugin().getConfig().getString("donateMenuItem"));
        if (borderItem == null) {
            borderItem = Material.DIAMOND_BLOCK;
        }
    }

    @Override
    protected void setContents() {
        ItemStack item = super.createItemStack(borderItem, " ");

        for (int slot = 0; slot < getSlots(); slot++)
            if (slot != inputSlot)
                getInventory().setItem(slot, item);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {

        if (event.getRawSlot() > getSlots()) {
            event.setCancelled(false);
            return;
        }

        InventoryAction action = event.getAction();
        switch (action) {

            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:

                if (getInventory().getItem(getInputSlot()) != null)
                    event.setCancelled(false);

                break;

            case PLACE_SOME:
            case PLACE_ALL:
            case PLACE_ONE:

                if (event.getRawSlot() != inputSlot)
                    return;

                ItemStack putDown = getInventory().getItem(getInputSlot());
                boolean isAcceptable = false;

                for (QuestController ctrl : getControllers()) {

                    QuestData data = ctrl.getQuestData();
                    double total = data.getAmountCompleted() + putDown.getAmount();
                    int goal = data.getQuestGoal();

                    List<String> requirements = ctrl.getEventConstraints().getMaterialNames();
                    if (requirements.isEmpty() || Utils.contains(requirements, putDown)) {

                        if (total > goal) {

                            int diff = (int) total - goal;

                            putDown.setAmount(putDown.getAmount() - diff);
                            getOwner().updateInventory();

                            ItemStack toCursor = event.getCursor().clone();
                            toCursor.setAmount(toCursor.getAmount() + diff);

                            getOwner().setItemOnCursor(toCursor);
                        }

                        updateQuest(ctrl, putDown);
                        isAcceptable = true;
                    }
                }

                if (!isAcceptable) {

                    String cannotDonate = getPlugin().messages().message("cantDonateItem");
                    getOwner().sendMessage(cannotDonate);
                }
            default:
                break;
        }
    }

    @Override
    public @NonNull List<QuestController> getControllers() {

        List<QuestController> controllers = new ArrayList<>();

        ActiveQuests activeQuests = getPlugin().config().getActiveQuests();
        for (QuestController ctrl : activeQuests.getActiveQuestsList())
            if (ctrl.getObjective().equals(ObjectiveType.GUI))
                controllers.add(ctrl);

        return controllers;
    }

    private void updateQuest(@NonNull QuestController ctrl, @NonNull ItemStack item) {

        if (!isWorldAllowed(ctrl, getOwner().getWorld()))
            return;

        if (ctrl.updateQuest(item.getAmount(), getOwner()))
            ctrl.endQuest();
    }

    private boolean isWorldAllowed(@NonNull QuestController ctrl, @NonNull World world) {

        List<String> worlds = ctrl.getEventConstraints().getWorlds();
        return worlds.isEmpty() || Utils.contains(worlds, world.getName());
    }
}
