package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.utils.Colorization;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        if (displayName == null || displayName.length() < 1) {
            this.displayName = materialName;
        } else {
            this.displayName = displayName;
        }

        this.item = new ItemStack(Material.matchMaterial(materialName), amount);
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {
        Material material = Material.getMaterial(materialName);

        if (amount <= 0 || material == null || !player.isOnline())
            return;

        ItemStack itemStack = new ItemStack(material, amount);
        if (!displayName.isEmpty()) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(color(displayName));
            itemStack.setItemMeta(meta);
        }

        Player realPlayer = (Player) player;
        HashMap<Integer, ItemStack> overFlowItems = realPlayer.getInventory().addItem(itemStack);
        if (overFlowItems.size() > 0) {
            realPlayer.sendMessage(color("&cInventory is full! Reward was dropped on the ground."));
            overFlowItems.forEach((slot, item) -> realPlayer.getWorld().dropItem(realPlayer.getLocation(), item));
        }
        realPlayer.sendMessage(color("- " + amount + " " + displayName));
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
