package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.rewards.*;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class QuestLibrary {

    private HashMap<String, QuestModel> questList;

    public QuestLibrary() {
    }

    public QuestModel getQuestModelById(String questId) {
        return questList.get(questId);
    }

    public void loadQuestConfiguration(ConfigurationSection serverQuestConfig) {
        HashMap<String, QuestModel> map = new HashMap<>();
        if (serverQuestConfig == null) {
            questList = map;
            return;
        }

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
        String questDuration = section.getString("questDuration");
        List<String> mobNames = section.getStringList("entities");
        List<String> itemNames = section.getStringList("materials");
        List<String> worlds = section.getStringList("worlds");
        String displayItem = section.getString("displayItem", "");

        ObjectiveType type = ObjectiveType.match(section.getString("type"));
        int goal = section.getInt("goal", -1);

        ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");
        ArrayList<Reward> rewards = getRewardsFromConfig(rewardsSection);
        int rewardsLimit = 0;
        if (rewardsSection != null) {
            rewardsLimit = rewardsSection.getInt("rewardLimit", 0);
        }

        return new QuestModel(questId, displayName, description, goal,
                type, mobNames, rewards, itemNames, displayItem, worlds, questDuration, rewardsLimit);
    }

    private ArrayList<Reward> getRewardsFromConfig(ConfigurationSection section) {
        ArrayList<Reward> rewards = new ArrayList<>();
        if (section == null) {
            return rewards;
        }

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
                        JavaPlugin.getPlugin(ServerQuests.class).getLogger()
                                .info("Item reward failed to load due to invalid configuration");
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
