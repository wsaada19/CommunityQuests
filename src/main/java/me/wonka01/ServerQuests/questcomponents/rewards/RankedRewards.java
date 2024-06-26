package me.wonka01.ServerQuests.questcomponents.rewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class RankedRewards {
    // make a map of rewards where the key is a string and the value is a reward
    @Getter
    private final Map<String, ArrayList<Reward>> rewards;

    public RankedRewards() {
        rewards = new HashMap<>();
    }

    public RankedRewards(Map<String, ArrayList<Reward>> rewards) {
        this.rewards = rewards;
    }
}
