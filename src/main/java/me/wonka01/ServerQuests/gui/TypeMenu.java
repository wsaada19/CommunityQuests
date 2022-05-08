package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class TypeMenu extends Menu {

    private final int coop = 12, comp = 14, back = 18;

    private final @NonNull QuestModel model;

    public TypeMenu(ServerQuests plugin, @NonNull Player owner, @NonNull QuestModel model) {

        super(plugin, owner, "typeMenu", 27);
        this.model = model;
    }

    @Override
    protected void setButtons() {

        String title = getPlugin().getMessages().string("goBack"),
            description = getPlugin().getMessages().string("goBackText");
        ItemStack backButton = super.createItemStack(Material.ARROW, title, Collections.singletonList(description));

        getInventory().addItem(backButton);
    }

    @Override
    protected void setContents() {

        getInventory().setItem(comp, getCompItem());
        getInventory().setItem(coop, getCoopItem());
    }

    private @NonNull ItemStack getCompItem() {

        Material material = Material.DIAMOND_SWORD;
        String title = getPlugin().getMessages().string("competitive");

        return super.createItemStack(material, title);
    }

    private @NonNull ItemStack getCoopItem() {

        Material material = model.getQuestGoal() < 0 ? Material.GOLDEN_APPLE : Material.AIR;
        String title = getPlugin().getMessages().string("cooperative");

        return super.createItemStack(material, title);
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {

        switch (event.getSlot()) {

            case comp:

                startQuest(EventType.COMPETITIVE);
                break;
            case coop:

                startQuest(EventType.COLLAB);
                break;
            case back:

                new StartMenu(getPlugin(), getOwner());
            default:
                return;
        }
        getOwner().closeInventory();
    }

    private void startQuest(@NonNull EventType type) {
        ActiveQuests.getActiveQuestsInstance().beginNewQuest(model, type);
    }
}
