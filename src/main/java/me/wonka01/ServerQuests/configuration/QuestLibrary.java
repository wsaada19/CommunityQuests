package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.rewards.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

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
            try {
                QuestModel model = loadQuestFromConfig(section);
                map.put(questId, model);
            } catch (Exception e) {
                Bukkit.getServer().getConsoleSender().sendMessage(
                        "[Community Quests] Failed to load quest with ID " + questId
                                + ". Please check the configuration file.");
                e.printStackTrace();
            }
        }
        questList = map;
    }

    private QuestModel loadQuestFromConfig(ConfigurationSection section) {
        String questId = section.getName();
        String displayName = section.getString("displayName");
        String description = section.getString("description");
        String questDuration = section.getString("questDuration");
        List<String> worlds = section.getStringList("worlds");
        String displayItem = section.getString("displayItem", "");
        String afterQuestCommand = section.getString("afterQuestCommand", "");
        String beforeQuestCommand = section.getString("beforeQuestCommand", "");
        String questFailedCommand = section.getString("questFailedCommand", "");

        List<Objective> objectives = null;
        List<String> mobNames = null;
        int goal = 0;
        ObjectiveType type = null;
        List<String> materials = null;

        List<LinkedHashMap> objectivesConfig = (List<LinkedHashMap>) section.getList("objectives");

        // If objectives are not defined use old quest config?
        if (objectivesConfig == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                    "[Community Quests] Using the legacy questing system for ID " + questId
                            + ". Please check the docs and follow the new format for creating quests with the objectives option. This enables you to set multiple objectives per quest.");
            mobNames = section.getStringList("entities");
            materials = section.getStringList("materials");
            type = ObjectiveType.match(section.getString("type"));
            goal = section.getInt("goal", -1);
        } else {
            objectives = new ArrayList<>();
            Bukkit.getServer().getConsoleSender().sendMessage(
                    "[Community Quests] Using the new questing system for ID " + questId
                            + ". This enables you to set multiple objectives per quest. This quest has "
                            + objectivesConfig.size() + " objectives.");
            for (LinkedHashMap obj : objectivesConfig) {
                String objectiveType = (String) obj.get("type");
                double objectiveGoal = 0.0;
                if (obj.get("goal") instanceof Integer) {
                    objectiveGoal = (Integer) obj.get("goal");
                } else if (obj.get("goal") instanceof Double) {
                    objectiveGoal = (Double) obj.get("goal");
                }
                String objDescription = (String) obj.get("description");
                List<String> objectiveMobs = (List<String>) obj.get("entities");
                List<String> objectiveMaterials = (List<String>) obj.get("materials");
                ObjectiveType objectiveTypeEnum = ObjectiveType.match(objectiveType);
                List<Material> mats = new ArrayList<>();
                if (objectiveMaterials != null) {
                    mats = objectiveMaterials.stream().map(itemName -> {
                        String capitalizedMaterialName = itemName.toUpperCase().replaceAll(" ", "_");
                        Material material = Material.getMaterial(capitalizedMaterialName);
                        if (material == null) {
                            return Material.AIR;
                        }
                        return material;
                    }).filter(material -> material != Material.AIR).collect(Collectors.toList());
                } else {
                    objectiveMaterials = new ArrayList<>();
                }

                if (objectiveMobs == null) {
                    objectiveMobs = new ArrayList<>();
                }

                Objective objective = new Objective(objectiveTypeEnum, objectiveGoal, 0.0, objectiveMobs,
                        mats, objDescription);
                objectives.add(objective);
            }
        }

        ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");
        ArrayList<Reward> rewards = getRewardsFromConfig(rewardsSection);
        int rewardsLimit = 0;
        if (rewardsSection != null) {
            rewardsLimit = rewardsSection.getInt("rewardsLimit", 0);
        }

        return new QuestModel(questId, displayName, description, goal,
                type, mobNames, rewards, materials, displayItem, worlds, questDuration, rewardsLimit, afterQuestCommand,
                beforeQuestCommand, objectives, questFailedCommand);
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
                if (itemRewards == null || itemRewards.isEmpty()) {
                    continue;
                }
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
