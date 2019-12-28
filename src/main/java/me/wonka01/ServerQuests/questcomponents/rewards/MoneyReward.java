package me.wonka01.ServerQuests.questcomponents.rewards;

import me.wonka01.ServerQuests.ServerQuests;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

public class MoneyReward implements Reward {

    private double amount;
    public MoneyReward(double amount)
    {
        this.amount = amount;
    }

    public void giveRewardToPlayer(OfflinePlayer player, double rewardPercentage) {

        double weightedAmount = rewardPercentage * amount;
        Economy economy = ServerQuests.economy;
        economy.depositPlayer(player, weightedAmount);
    }
}
