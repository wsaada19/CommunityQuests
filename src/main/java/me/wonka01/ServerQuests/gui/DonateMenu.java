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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DonateMenu extends Menu {

    @Getter
    private final int inputSlot = 22;
    private final ItemStack borderItem;

    public DonateMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "donateMenu", 45);

        Material borderMaterial = Material.getMaterial(
            getPlugin().getConfig().getString("donateMenuItem", "BLACK_STAINED_GLASS_PANE"));
        if (borderMaterial == null) {
            borderMaterial = Material.BLACK_STAINED_GLASS_PANE;
        }
        borderItem = createItemStack(borderMaterial, " ");
    }

    @Override
    protected void setBorder() {
        for (int slot = 0; slot < getSlots(); slot++)
            if (slot != inputSlot)
                getInventory().setItem(slot, borderItem);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        if (!event.getView().getTopInventory().equals(event.getClickedInventory()) && !event.isShiftClick()) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null && clickedItem.isSimilar(borderItem)) {
            event.setCancelled(true);
            return;
        }

        if (getInventory().getItem(inputSlot) != null) {
            return;
        }

        ItemStack inputItem = event.getCursor();
        if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
            if (event.getClick() == ClickType.NUMBER_KEY) {
                inputItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            } else {
                inputItem = event.getWhoClicked().getInventory().getItemInOffHand();
            }
        } else if (event.isShiftClick()) {
            inputItem = event.getCurrentItem();
        }

        if (inputItem == null || inputItem.getType() == Material.AIR) {
            return;
        }

        event.setCancelled(true);
        boolean isAcceptable = false;
        for (QuestController ctrl : getControllers()) {

            QuestData data = ctrl.getQuestData();
            double total = data.getAmountCompleted() + inputItem.getAmount();
            int goal = data.getQuestGoal();

            List<Material> requirements = ctrl.getEventConstraints().getMaterials();
            if (requirements.isEmpty() || requirements.contains(inputItem.getType())) {
                updateQuest(ctrl, inputItem);

                if (total > goal) {
                    int diff = (int) total - goal;
                    inputItem.setAmount(diff);
                } else {
                    inputItem.setAmount(0);
                }

                isAcceptable = true;
            }
        }

        if (!isAcceptable) {
            String cannotDonate = getPlugin().messages().message("cantDonateItem");
            getOwner().sendMessage(cannotDonate);
        }
    }

    @Override
    protected void onClose(@NonNull InventoryCloseEvent event) {

        ItemStack leftover = getInventory().getItem(getInputSlot());
        if (leftover == null)
            return;

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
