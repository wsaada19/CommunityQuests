package me.wonka01.ServerQuests.configuration;

import lombok.Getter;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.rewards.Reward;
import me.wonka01.ServerQuests.questcomponents.schedulers.ParseDurationString;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class QuestModel {
    private final String questId;
    private final String displayName;
    private final String eventDescription;
    private final int completeTime;
    private final int questGoal;
    private List<Objective> objectives;
    private final List<String> mobNames;
    private final ArrayList<Reward> rewards;
    private final Material displayItem;
    private final List<String> worlds;
    private final String questDuration;
    private final int rewardLimit;
    private final String afterQuestCommand;
    private final String beforeQuestCommand;
    private final String questFailedCommand;
    private final String barColor;
    private final Map<String, ArrayList<Reward>> rankedRewards;
    private final Map<String, String> rankedRewardMessages;

    public QuestModel(String questId, String displayName, String eventDescription,
            int questGoal, ObjectiveType objective,
            List<String> mobNames, ArrayList<Reward> rewards, List<String> itemNames, String displayItem,
            List<String> worlds, String questDuration, int rewardLimit, String afterQuestCommand,
            String beforeQuestCommand, List<Objective> objectives, String questFailedCommand,
            List<String> customNames, String barColor, Map<String, ArrayList<Reward>> rankedRewards,
            Map<String, String> rankedRewardMessages) {
        this.questId = questId;
        this.displayName = displayName;
        this.eventDescription = eventDescription;
        this.completeTime = ParseDurationString.parseStringToSeconds(questDuration);
        this.questGoal = questGoal;
        this.questFailedCommand = questFailedCommand;
        this.barColor = barColor;

        List<Material> materials = new ArrayList<>();
        if (itemNames != null) {
            materials = itemNames.stream().map(itemName -> {
                String capitalizedMaterialName = itemName.toUpperCase().replaceAll(" ", "_");
                Material material = Material.getMaterial(capitalizedMaterialName);
                if (material == null) {
                    return Material.AIR;
                }
                return material;
            }).filter(material -> material != Material.AIR).collect(Collectors.toList());
        }

        if (objectives != null) {
            this.objectives = objectives;
        } else {
            this.objectives = Arrays
                    .asList(new Objective(objective, questGoal * 1.0, 0, mobNames, materials, objective.getString(),
                            customNames));
        }
        this.mobNames = mobNames;
        this.rewards = rewards;
        this.rankedRewards = rankedRewards;
        this.rankedRewardMessages = rankedRewardMessages;
        // this.itemNames = materials;
        this.worlds = worlds;
        this.questDuration = questDuration;
        this.rewardLimit = rewardLimit;
        this.afterQuestCommand = afterQuestCommand;
        this.beforeQuestCommand = beforeQuestCommand;

        if (Utils.contains(Material.values(), displayItem)) {
            this.displayItem = Material.valueOf(displayItem.toUpperCase());
        } else {
            if (objectives != null && objectives.size() > 0) {
                this.displayItem = objectives.get(0).getType().getDefaultMaterial();
            } else if (objective != null) {
                this.displayItem = objective.getDefaultMaterial();
            } else {
                this.displayItem = Material.DIAMOND_SHOVEL;
            }
        }
    }
}
