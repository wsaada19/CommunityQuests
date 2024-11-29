package me.wonka01.ServerQuests.gui;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.knighthat.apis.menus.Menu;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.objectives.ObjectiveFilters;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;

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
    @Getter
    @Setter
    private static ItemStack borderItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    public DonateMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "donateMenu", 45);
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
        // TODO - make sure that they right amount is taken even if there's multiple
        // quests
        for (QuestController ctrl : getControllers()) {
            int counter = 0;
            for (int i = 0; i < ctrl.getQuestData().getObjectives().size(); i++) {
                Objective objective = ctrl.getQuestData().getObjectives().get(i);

                if (objective.getType() != ObjectiveType.GUI)
                    continue;

                double total = objective.getAmountComplete() + inputItem.getAmount();
                double goal = objective.getGoal();

                if (ctrl.isCompetitive()) {
                    CompetitiveQuestData data = (CompetitiveQuestData) ctrl.getQuestData();
                    total = data.getPlayers().getAmountContributedByObjectiveId(getOwner(), i) + inputItem.getAmount();
                }

                boolean matches = ObjectiveFilters.filter()
                        .withItem(inputItem)
                        .matches(objective);

                if (matches) {
                    boolean updateResult = updateQuest(ctrl, inputItem, objective, counter);
                    if (updateResult) {
                        if (total > goal && goal > 0) {
                            int diff = (int) total - (int) goal;
                            inputItem.setAmount(diff);
                        } else {
                            inputItem.setAmount(0);
                        }
                    } else {
                        continue;
                    }
                    isAcceptable = true;
                }
                counter++;
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
            if (ctrl.getObjectiveTypes().contains(ObjectiveType.GUI))
                controllers.add(ctrl);

        return controllers;
    }

    private boolean updateQuest(@NonNull QuestController ctrl, @NonNull ItemStack item, Objective obj,
            int objectiveId) {

        if (!isWorldAllowed(ctrl, getOwner().getWorld()))
            return false;

        ctrl.updateQuest(item.getAmount(), getOwner(), obj, objectiveId);
        return true;
    }

    private boolean isWorldAllowed(@NonNull QuestController ctrl, @NonNull World world) {
        List<String> worlds = ctrl.getEventConstraints().getWorlds();
        return worlds.isEmpty() || Utils.contains(worlds, world.getName());
    }
}
