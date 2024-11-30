package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.rewards.*;
import me.wonka01.ServerQuests.questcomponents.rewards.types.CommandReward;
import me.wonka01.ServerQuests.questcomponents.rewards.types.ExperienceReward;
import me.wonka01.ServerQuests.questcomponents.rewards.types.ItemReward;
import me.wonka01.ServerQuests.questcomponents.rewards.types.MoneyReward;
import me.wonka01.ServerQuests.questcomponents.rewards.types.Reward;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public class QuestLibrary {

    private HashMap<String, QuestModel> questList;

    public QuestLibrary() {
    }

    public QuestModel getQuestModelById(String questId) {
        return questList.get(questId);
    }

    public boolean containsQuest(String questId) {
        return questList.containsKey(questId);
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

    public String getRandomQuest() {
        List<String> keys = new ArrayList<>(questList.keySet());
        if (keys.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return keys.get(random.nextInt(keys.size()));
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
        String barColor = section.getString("barColor", "");
        String barStyle = section.getString("barStyle", "SOLID");
        BarStyle style = BarStyle.valueOf(barStyle.toUpperCase());

        List<Objective> objectives = null;
        List<String> entityNames = null;
        List<String> customNames = null;

        int goal = 0;
        ObjectiveType type = null;
        List<String> materials = null;

        List<LinkedHashMap> objectivesConfig = (List<LinkedHashMap>) section.getList("objectives");

        // If objectives are not defined use old quest config?
        if (objectivesConfig == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                    "[Community Quests] Using the legacy questing system for ID " + questId
                            + ". Please check the docs and follow the new format for creating quests with the objectives option. This enables you to set multiple objectives per quest.");
            entityNames = section.getStringList("entities");
            materials = section.getStringList("materials");
            customNames = section.getStringList("customNames");

            type = ObjectiveType.match(section.getString("type"));
            goal = section.getInt("goal", -1);
        } else {
            objectives = new ArrayList<>();
            for (LinkedHashMap obj : objectivesConfig) {
                String objectiveType = (String) obj.get("type");
                double objectiveGoal = 0.0;
                if (obj.get("goal") instanceof Integer) {
                    objectiveGoal = (Integer) obj.get("goal");
                } else if (obj.get("goal") instanceof Double) {
                    objectiveGoal = (Double) obj.get("goal");
                }
                String dynamicGoal = (String) obj.get("dynamicGoal");
                String objDescription = (String) obj.get("description");
                List<String> objectiveMobs = (List<String>) obj.get("entities");
                List<String> objectiveMaterials = (List<String>) obj.get("materials");
                List<String> objectiveCustomNames = (List<String>) obj.get("customNames");
                List<Integer> objectiveModelIds = (List<Integer>) obj.get("modelIds");
                List<String> potionNames = (List<String>) obj.get("potions");
                List<String> enchantments = (List<String>) obj.get("enchantments");

                if (potionNames != null) {
                    objectiveCustomNames = potionNames;
                }

                if (enchantments != null) {
                    objectiveCustomNames = enchantments;
                }

                ObjectiveType objectiveTypeEnum = ObjectiveType.match(objectiveType);
                List<Material> mats = new ArrayList<>();
                if (objectiveMaterials != null) {
                    mats = objectiveMaterials.stream().map(itemName -> {
                        String capitalizedMaterialName = itemName.toUpperCase().replaceAll(" ", "_");
                        Material material = Material.getMaterial(capitalizedMaterialName);
                        if (material == null) {
                            Bukkit.getServer().getConsoleSender().sendMessage(
                                    "[Community Quests] Invalid material name " + itemName + " for quest " + questId);
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
                if (objectiveCustomNames == null) {
                    objectiveCustomNames = new ArrayList<>();
                }
                if (objectiveModelIds == null) {
                    objectiveModelIds = new ArrayList<>();
                }

                Objective objective = new Objective(objectiveTypeEnum, objectiveGoal, 0.0, objectiveMobs,
                        mats, objDescription, objectiveCustomNames, dynamicGoal, objectiveModelIds);
                objectives.add(objective);
            }
        }

        ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");
        ConfigurationSection rankedRewards = null;
        int rewardsLimit = 0;
        if (rewardsSection != null) {
            rewardsLimit = rewardsSection.getInt("rewardsLimit", 0);
            rankedRewards = rewardsSection.getConfigurationSection("rankedRewards");
        }

        Map<String, ArrayList<Reward>> rankedRewardsMap = new HashMap<>();
        Map<String, String> rankedRewardMessages = new HashMap<>();
        if (rankedRewards != null) {
            for (String key : rankedRewards.getKeys(false)) {
                ConfigurationSection rewardSection = rankedRewards.getConfigurationSection(key);
                ArrayList<Reward> rewards = getRewardsFromConfig(rewardSection);
                String message = rewardSection.getString("rewardMessage", "");
                rankedRewardsMap.put(key, rewards);
                rankedRewardMessages.put(key, message);
            }
        } else {
            rankedRewardsMap = new HashMap<>();
            rankedRewardsMap.put("*", getRewardsFromConfig(rewardsSection));
        }

        return new QuestModel(questId, displayName, description, goal,
                type, entityNames, materials, displayItem, worlds, questDuration, rewardsLimit, afterQuestCommand,
                beforeQuestCommand, objectives, questFailedCommand, customNames, barColor.toUpperCase(),
                rankedRewardsMap,
                rankedRewardMessages, style);
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
            } else if (key.equalsIgnoreCase("rewardMessage")) {
                String message = section.getString("rewardMessage");
                reward = new RewardMessage(message);
                rewards.add(reward);
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
                        Bukkit.getLogger()
                                .info("Item reward failed to load due to invalid configuration");
                        Bukkit.getLogger()
                                .info(ex.getMessage());
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
