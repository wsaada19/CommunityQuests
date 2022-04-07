package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.rewards.*;
import me.wonka01.ServerQuests.util.ObjectiveTypeUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

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
        int timeToComplete = section.getInt("timeToComplete", 0);
        List<String> mobNames = section.getStringList("entities");
        List<String> itemNames = section.getStringList("materials");

        ObjectiveType objectiveType = ObjectiveTypeUtil.parseEventTypeFromString(section.getString("type"));
        int goal = section.getInt("goal", -1);

        ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");

        ArrayList<Reward> rewards;
        if (rewardsSection == null) {
            rewards = new ArrayList<>();
        } else {
            rewards = getRewardsFromConfig(rewardsSection);
        }

        return new QuestModel(questId, displayName, description, timeToComplete, goal,
                objectiveType, mobNames, rewards, itemNames);
    }

    // TODO Clean this up please
    private ArrayList<Reward> getRewardsFromConfig(ConfigurationSection section) {
        ArrayList<Reward> rewards = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            Reward reward;
            if (key.equalsIgnoreCase("money")) {
                double amount = section.getDouble("money");
                reward = new MoneyReward(amount);
                rewards.add(reward);

            } else if (key.equalsIgnoreCase("experience")) {
                int amount = section.getInt("experience");
                reward = new ExperienceReward(amount);
                rewards.add(reward);

            } else if (key.equalsIgnoreCase("commands")) {
                List<String> commands = section.getStringList("commands");
                for (String command : commands) {
                    Reward commandReward = new CommandReward(command);
                    rewards.add(commandReward);
                }
            } else if (key.equalsIgnoreCase("items")) {
                List<?> itemRewards = section.getList(key);
                for (Object item : itemRewards) {
                    try {
                        LinkedHashMap<?, ?> map = (LinkedHashMap) item;
                        int amount = (Integer) map.get("amount");
                        String material = (String) map.get("material");
                        String itemName = (String) map.get("displayName");
                        reward = new ItemReward(amount, material, itemName);
                        rewards.add(reward);
                    } catch (Exception ex) {
                        JavaPlugin.getPlugin(ServerQuests.class).getLogger().info("Item reward failed to load due to invalid configuration");
                    }
                }
            }
        }
        return rewards;
    }

    public Set<String> getAllQuestKeys() {
        return questList.keySet();
    }

}
