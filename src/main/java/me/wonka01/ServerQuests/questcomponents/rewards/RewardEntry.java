package me.wonka01.ServerQuests.questcomponents.rewards;

import lombok.Getter;

@Getter
public class RewardEntry {
    public Reward reward;
    public double ratio;

    public RewardEntry(Reward reward, double ratio) {
        this.reward = reward;
        this.ratio = ratio;
    }
}