package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class TypeMenu extends Menu {

    private final int COMP = 12, COOP = 14, BACK = 18;

    private final @NonNull QuestModel model;

    public TypeMenu(ServerQuests plugin, @NonNull Player owner, @NonNull QuestModel model) {
        super(plugin, owner, "typeMenu", 27);
        this.model = model;
    }

    @Override
    protected void setButtons() {
        String title = getPlugin().messages().string("goBack"),
                description = getPlugin().messages().string("goBackText");
        ItemStack backButton = createItemStack(Material.ARROW, title, Collections.singletonList(description));

        getInventory().setItem(BACK, backButton);
    }

    @Override
    protected void setContents() {
        getInventory().setItem(COMP, getCompItem());
        getInventory().setItem(COOP, getCoopItem());
    }

    private @NonNull ItemStack getCompItem() {
        Material material = Material.DIAMOND_SWORD;
        String title = getPlugin().messages().string("competitive");
        return createItemStack(material, title);
    }

    private @NonNull ItemStack getCoopItem() {
        Material material = model.getObjectives().stream().mapToDouble(Objective::getGoal).sum() > 0
                ? Material.GOLDEN_APPLE
                : Material.AIR;
        String title = getPlugin().messages().string("cooperative");
        return createItemStack(material, title);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        switch (event.getSlot()) {
            case COMP:
                startQuest(EventType.COMPETITIVE);
                break;
            case COOP:
                startQuest(EventType.COLLAB);
                break;
            case BACK:
                new StartMenu(getPlugin(), getOwner()).open();
            default:
                return;
        }
        getOwner().closeInventory();
    }

    private void startQuest(@NonNull EventType type) {
        ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, type);
    }
}
