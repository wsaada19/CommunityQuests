package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemReward implements Reward {

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

        if (material != null) {
            ItemStack itemStack = new ItemStack(material, amount);
            if (player.isOnline()) {
                Player realPlayer = (Player) player;
                realPlayer.getInventory().addItem(itemStack);
                realPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + amount + " " + displayName));
            }
        }
    }
}
