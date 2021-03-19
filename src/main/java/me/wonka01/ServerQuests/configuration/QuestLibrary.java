package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.rewards.*;
import me.wonka01.ServerQuests.util.ObjectiveTypeUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class QuestLibrary {

    private HashMap<String, QuestModel> questList;

    public QuestLibrary() {
    }

    public QuestModel getQuestModelById(String questId) {
        if (questList.containsKey(questId)) {
            return questList.get(questId);
        } else {
            return null;
        }
    }

    public void loadQuestConfiguration(ConfigurationSection serverQuestConfig) {
        HashMap<String, QuestModel> map = new HashMap<String, QuestModel>();
        for (String questId : serverQuestConfig.getKeys(false)) {
            ConfigurationSection section = serverQuestConfig.getConfigurationSection(questId);
            QuestModel model = loadQuestFromConfig(section);

            map.put(questId, model);
        }
        questList = map;
    }

    private QuestModel loadQuestFromConfig(ConfigurationSection section) {
        String questId = section.getName();
        String displayName = section.getString("displayName");
        String description = section.getString("description");
        int timeToComplete = section.getInt("timeToComplete");
        int goal = section.getInt("goal");
        List<String> mobNames = section.getStringList("entities");
        List<String> itemNames = section.getStringList("materials");

        ObjectiveType objectiveType = ObjectiveTypeUtil.parseEventTypeFromString(section.getString("type"));
        ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");

        ArrayList<Reward> rewards;
        if (rewardsSection == null) {
            rewards = new ArrayList<Reward>();
        } else {
            rewards = getRewardsFromConfig(rewardsSection);
        }

        return new QuestModel(questId, displayName, description, timeToComplete, goal,
                objectiveType, mobNames, rewards, itemNames);
    }

    // TODO Clean this up please
    private ArrayList<Reward> getRewardsFromConfig(ConfigurationSection section) {
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        for (String key : section.getKeys(false)) {
            Reward reward;
            if (key.equalsIgnoreCase("money")) {
                double amount = section.getDouble("money");
                reward = new MoneyReward(amount);

            } else if (key.equalsIgnoreCase("experience")) {
                int amount = section.getInt("experience");
                reward = new ExperienceReward(amount);

            } else if (key.equalsIgnoreCase("commands")) {
                List<String> commands = section.getStringList("commands");
                for(String command : commands) {
                    Reward commandReward = new CommandReward(command);
                    rewards.add(commandReward);
                }
                continue;
            } else {
                ConfigurationSection rewardSection = section.getConfigurationSection(key);
                int amount = rewardSection.getInt("amount");
                String material = rewardSection.getString("material");
                String itemName = rewardSection.getString("displayName");
                reward = new ItemReward(amount, material, itemName);
            }
            rewards.add(reward);
        }
        return rewards;
    }

    public Set<String> getAllQuestKeys() {
        return questList.keySet();
    }

}