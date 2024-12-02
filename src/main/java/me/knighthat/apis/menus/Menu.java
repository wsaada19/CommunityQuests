package me.knighthat.apis.menus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.schedulers.ParseDurationString;
import me.knighthat.apis.utils.Colorization;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class Menu implements InventoryHolder, Colorization {

    @Getter(value = AccessLevel.PROTECTED)
    private final ServerQuests plugin;

    private final @NonNull Inventory inventory;

    private final int slots;

    private final @NonNull Player owner;

    private final @NonNull List<QuestController> controllers = ActiveQuests.getActiveQuestsInstance()
            .getActiveQuestsList();

    protected Menu(ServerQuests plugin, @NonNull Player owner, @NonNull String titlePath, int slots) {

        this.plugin = plugin;
        this.owner = owner;
        this.slots = slots;

        String title = plugin.messages().string(titlePath);
        this.inventory = Bukkit.createInventory(this, slots, title);
    }

    protected void setBorder() {
    }

    protected void setButtons() {
    }

    protected void setContents() {
    }

    protected void onItemClick(@NonNull final InventoryClickEvent event) {
    }

    protected void onClose(@NonNull final InventoryCloseEvent event) {
    }

    public void open() {
        try {
            setBorder();
            setButtons();
            setContents();
            owner.openInventory(inventory);
        } catch (Exception exception) {
            Bukkit.getLogger().warning("Error opening menu: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    protected @NonNull ItemStack createItemStack(@NonNull Material m, @NonNull String n) {
        return createItemStack(m, n, new ArrayList<>());
    }

    protected @NonNull ItemStack createItemStack(@NonNull Material m, @NonNull String n, @NonNull List<String> l) {

        ItemStack item = new ItemStack(m);

        try {
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(color(n));
            meta.setLore(l.stream().map(this::color).collect(Collectors.toList()));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            item.setItemMeta(meta);
        } catch (NullPointerException ignored) {
        }
        return item;
    }

    protected @NonNull List<String> getLoreFromData(@NonNull QuestData data) {
        List<String> lore = new ArrayList<>();
        String[] description = data.getDescriptionArr();
        for (String s : description) {
            lore.add(color(s));
        }
        lore.add(" ");

        int duration = data.getQuestDuration();
        if (duration > 0) {

            if (data.getQuestDuration() > 0) {
                lore.add(getPlugin().messages().string("timeRemaining"));
                String durationString = "&a" + ParseDurationString.formatSecondsToString(duration);
                lore.add(color(durationString));
                lore.add(" ");
            }
        }

        return lore;
    }
}
