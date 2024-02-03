package me.wonka01.ServerQuests.gui;

import lombok.NonNull;
import me.knighthat.apis.menus.Menu;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardEntry;
import me.wonka01.ServerQuests.questcomponents.rewards.RewardManager;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RewardsMenu extends Menu {
    private final int CLAIM = 0;
    private final @NonNull RewardManager rewards;
    private ArrayList<RewardEntry> rewardsList;

    public RewardsMenu(ServerQuests plugin, @NonNull Player owner) {
        super(plugin, owner, "Claim your rewards", 9);
        rewards = RewardManager.getInstance();
        rewardsList = rewards.getRewards(owner.getUniqueId());
    }

    @Override
    protected void setContents() {
        getInventory().setItem(CLAIM, getRewardItem());
    }

    @Override
    protected void onItemClick(@NonNull InventoryClickEvent event) {
        if (event.getRawSlot() == CLAIM) {
            for (RewardEntry reward : rewardsList) {
                reward.getReward().giveRewardToPlayer(getOwner(), reward.getRatio());
            }
            rewards.removeRewards(getOwner().getUniqueId());
            getOwner().closeInventory();
        }
    }

    private ItemStack getRewardItem() {
        return createItemStack(Material.DIAMOND_BLOCK, getPlugin().messages().string("claimRewards"));
    }
}
