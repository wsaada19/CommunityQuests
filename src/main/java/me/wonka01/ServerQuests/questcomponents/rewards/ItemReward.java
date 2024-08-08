package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

public class ItemReward implements Reward, Colorization {

    private final String materialName;
    private final int amount;
    private final String displayName;
    @Getter
    private final @NonNull ItemStack item;

    public ItemReward(int count, String materialName, @Nullable String displayName) {
        this.amount = count;
        this.materialName = materialName;
        if (displayName != null && !displayName.isEmpty()) {
            this.displayName = displayName;
        } else {
            this.displayName = "";
        }

        this.item = new ItemStack(Material.matchMaterial(materialName), amount);
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        Material material = Material.getMaterial(materialName.toUpperCase());

        if (amount <= 0 || material == null || !player.isOnline())
            return;

        List<ItemStack> itemStack = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color(displayName));
            item.setItemMeta(meta);

            itemStack.add(item);
        }

        Player realPlayer = (Player) player;

        for (ItemStack item : itemStack) {
            HashMap<Integer, ItemStack> overFlowItems = realPlayer.getInventory().addItem(item);
            if (overFlowItems.size() > 0) {
                ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
                String inventoryFullMessage = plugin.messages().message("droppedReward");
                realPlayer.sendMessage(color(inventoryFullMessage));
                overFlowItems.forEach((slot, itm) -> realPlayer.getWorld().dropItem(realPlayer.getLocation(), itm));
            }
        }

        // if (displayName == null || displayName.isEmpty()) {
        // realPlayer.sendMessage(color("- &a" + amount + " &f" +
        // material.name().replace("_", " ").toLowerCase()));
        // } else {
        // realPlayer.sendMessage(color("- &a" + amount + " " + displayName));
        // }
    }

    @Override
    public String toString() {
        return "Item \n Material: " + materialName;
    }

    @Override
    public String getType() {
        return "item";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("material", materialName);
        json.put("amount", amount);
        json.put("displayName", displayName);
        return json;
    }
}
