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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    protected void setBorder() {
        ItemStack item = super.createItemStack(borderItem, " ");

        for (int slot = 0; slot < getSlots(); slot++)
            if (slot != inputSlot)
                getInventory().setItem(slot, item);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {

        InventoryAction action = event.getAction();
        switch (action) {

            case PLACE_SOME:
            case PLACE_ALL:
            case PLACE_ONE:

                if (event.getSlot() == inputSlot)
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            ItemStack putDown = getInventory().getItem(getInputSlot());
                            boolean isAcceptable = false;

                            for (QuestController ctrl : getControllers()) {

                                QuestData data = ctrl.getQuestData();
                                double total = data.getAmountCompleted() + putDown.getAmount();
                                int goal = data.getQuestGoal();

                                List<String> requirements = ctrl.getEventConstraints().getMaterialNames();
                                if (requirements.isEmpty() || Utils.contains(requirements, putDown.getType())) {

                                    int remaining = 0;

                                    if (total > goal) {

                                        int diff = (int) total - goal;
                                        remaining = diff;

                                        ItemStack toCursor = event.getCursor().clone();
                                        toCursor.setAmount(toCursor.getAmount() + diff);

                                        getOwner().setItemOnCursor(toCursor);
                                    }

                                    updateQuest(ctrl, putDown);
                                    isAcceptable = true;

                                    putDown.setAmount(remaining);
                                    getOwner().updateInventory();
                                }
                            }

                            if (!isAcceptable) {

                                String cannotDonate = getPlugin().messages().message("cantDonateItem");
                                getOwner().sendMessage(cannotDonate);
                            }

                        }
                    }.runTaskLater(getPlugin(), 3);

            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
                if (event.getSlot() == inputSlot) {
                    break;
                } else return;

            default:
                return;
        }
        event.setCancelled(false);
    }

    @Override
    protected void onClose(@NonNull InventoryCloseEvent event) {

        ItemStack leftover = getInventory().getItem(getInputSlot());
        if (leftover == null) return;

        PlayerInventory inventory = getOwner().getInventory();
        if (inventory.firstEmpty() > -1) {

            Map<Integer, ItemStack> items = inventory.addItem(leftover);
            items.forEach((slot, item) -> dropIt(item));
        } else
            dropIt(leftover);
    }

    private void dropIt(@NonNull ItemStack item) {
        getOwner().getWorld().dropItemNaturally(getOwner().getLocation(), item);
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
