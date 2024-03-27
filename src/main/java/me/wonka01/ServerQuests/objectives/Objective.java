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
    private final double goal;
    @Setter
    private double amountComplete;
    private List<String> mobNames;
    private List<Material> materials;

    public Objective(ObjectiveType type, double goal, double amountComplete, List<String> mobNames,
            List<Material> materials) {
        this.type = type;
        this.goal = goal;
        this.amountComplete = amountComplete;
        this.mobNames = mobNames;
        this.materials = materials;
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

    public JSONObject getObjectiveJSON() {
        JSONObject objectiveJson = new JSONObject();
        objectiveJson.put("type", type.getString());
        objectiveJson.put("goal", goal);
        objectiveJson.put("amountComplete", amountComplete);
        Gson gson = new Gson();
        objectiveJson.put("mobNames", gson.toJsonTree(mobNames).getAsJsonArray());
        objectiveJson.put("materials", gson.toJsonTree(materials).getAsJsonArray());
        return objectiveJson;
    }

    public Objective clone() {
        return new Objective(type, goal, amountComplete, mobNames, materials);
    }
}
