package me.wonka01.ServerQuests.objectives;

import java.util.List;

import org.bukkit.Material;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import me.wonka01.ServerQuests.enums.ObjectiveType;

@Getter
public class Objective implements Cloneable {
    private final ObjectiveType type;
    @Setter
    private double goal;
    @Setter
    private double amountComplete;
    private List<String> mobNames;
    private List<Material> materials;
    private List<String> customNames;
    private List<Integer> customModelIds;
    private String description;
    private String dynamicGoal;

    public Objective(ObjectiveType type, double goal, double amountComplete, List<String> mobNames,
            List<Material> materials, String description, List<String> customNames, String dynamicGoal,
            List<Integer> customModelIds) {
        this.type = type;
        this.goal = goal;
        this.amountComplete = amountComplete;
        this.mobNames = mobNames;
        this.materials = materials;
        this.description = description;
        this.customNames = customNames;
        this.dynamicGoal = dynamicGoal;
        this.customModelIds = customModelIds;
    }

    public boolean isGoalComplete() {
        return amountComplete >= goal;
    }

    public boolean hasGoal() {
        return goal > 0;
    }

    public void addToObjectiveProgress(double amount) {
        amountComplete += amount;
    }

    public JSONObject toJsonObject() {
        JSONObject objectiveJson = new JSONObject();
        objectiveJson.put("type", type.getString());
        objectiveJson.put("goal", goal);
        objectiveJson.put("amountComplete", amountComplete);
        Gson gson = new Gson();
        objectiveJson.put("mobNames", gson.toJsonTree(mobNames).getAsJsonArray());
        objectiveJson.put("customMobNames", gson.toJsonTree(customNames).getAsJsonArray());
        objectiveJson.put("customModelIds", gson.toJsonTree(customModelIds).getAsJsonArray());
        objectiveJson.put("materials", gson.toJsonTree(materials).getAsJsonArray());
        objectiveJson.put("description", description);
        return objectiveJson;
    }

    public Objective clone() {
        return new Objective(type, goal, amountComplete, mobNames, materials, description, customNames, dynamicGoal,
                customModelIds);
    }
}
