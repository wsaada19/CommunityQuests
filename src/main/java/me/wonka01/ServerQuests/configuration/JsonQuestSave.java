package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestTypeHandler;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import java.lang.reflect.Type;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class JsonQuestSave {

    private final ServerQuests plugin;
    private final File path;
    private final ActiveQuests activeQuests;

    public JsonQuestSave(ServerQuests plugin, File path) {
        this.plugin = plugin;
        this.path = new File(path + "/questSave.json");
        this.activeQuests = plugin.config().getActiveQuests();
    }

    public boolean getOrCreateQuestFile() {
        if (path.exists()) {
            return true;
        } else {
            try {
                path.createNewFile();
                saveQuestsInProgress();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
            }
        }
        return false;
    }

    public void saveQuestsInProgress() {
        JSONArray jsonArray = new JSONArray();
        for (QuestController questController : activeQuests.getActiveQuestsList()) {
            JSONObject jObject = new JSONObject();
            jObject.put("id", questController.getQuestData().getQuestId());
            jObject.put("playerMap", questController.getPlayerComponent().toJSONArray());
            JSONArray objectives = new JSONArray();
            for (int i = 0; i < questController.getQuestData().getObjectives().size(); i++) {
                objectives.add(questController.getQuestData().getObjectives().get(i).toJsonObject());
            }
            jObject.put("objectives", objectives);
            jObject.put("timeLeft", questController.getQuestData().getQuestDuration());
            if (questController.getQuestData() instanceof CompetitiveQuestData) {
                jObject.put("type", "comp");
            } else {
                jObject.put("type", "coop");
            }
            jsonArray.add(jObject);
        }
        try {
            FileWriter fileWriter = new FileWriter(path, false); // overwrite the existing file
            JSONObject object = new JSONObject();
            object.put("activeQuests", jsonArray);
            fileWriter.write(object.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public <T> List<T> fromArrayToList(T[] a) {
    // return Arrays.stream(a).collect(Collectors.toList());
    // }

    private <T> List<T> convertJsonArrayToList(JSONArray arr, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (arr == null) {
            return list;
        }
        for (Object o : arr) {
            list.add(clazz.cast(o));
        }
        return list;
    }

    public void readAndInitializeQuests() {
        if (!path.exists()) {
            return;
        }

        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader(path.getPath());
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray questArray = (JSONArray) object.get("activeQuests");
            Iterator qIterator = questArray.iterator();

            while (qIterator.hasNext()) {
                JSONObject questObject = (JSONObject) qIterator.next();
                String questId = (String) questObject.get("id");
                String questType = (String) questObject.get("type");
                JSONArray objectiveArray = (JSONArray) questObject.get("objectives");
                Iterator<JSONObject> oIterator = objectiveArray.iterator();
                List<Objective> objectives = new ArrayList<>();
                while (oIterator.hasNext()) {
                    JSONObject obj = oIterator.next();
                    String type = (String) obj.get("type");
                    double goal = (double) obj.get("goal");
                    String dynamicGoal = (String) obj.getOrDefault("dynamicGoal", "");
                    double amount = (double) obj.get("amountComplete");
                    JSONArray mobNames = (JSONArray) obj.get("mobNames");
                    JSONArray materials = (JSONArray) obj.get("materials");
                    JSONArray customNames = (JSONArray) obj.get("customMobNames");
                    JSONArray customModelIds = (JSONArray) obj.get("customModelIds");

                    List<Material> materialList = convertJsonArrayToList(materials, String.class).stream()
                            .map(materialName -> {
                                String capitalizedMaterialName = materialName.toUpperCase().replaceAll(" ", "_");
                                Material material = Material.getMaterial(capitalizedMaterialName);
                                if (material == null) {
                                    return Material.AIR;
                                }
                                return material;
                            }).collect(java.util.stream.Collectors.toList());

                    ObjectiveType objectiveType = ObjectiveType.match(type);
                    Objective objective = new Objective(objectiveType, goal, amount,
                            convertJsonArrayToList(mobNames, String.class),
                            materialList, (String) obj.get("description"),
                            convertJsonArrayToList(customNames, String.class),
                            dynamicGoal, convertJsonArrayToList(customModelIds, Integer.class));
                    objectives.add(objective);
                }
                long questDuration = (Long) questObject.getOrDefault("timeLeft", 0);

                JSONArray playerObject = (JSONArray) questObject.get("playerMap");
                Iterator<JSONObject> pIterator = playerObject.iterator();
                Map<UUID, PlayerData> playerMap = new TreeMap<>();
                while (pIterator.hasNext()) {
                    JSONObject obj = pIterator.next();
                    String key = (String) obj.keySet().iterator().next();
                    UUID uuid = UUID.fromString(key);
                    String playerName = Bukkit.getServer().getOfflinePlayer(uuid).getName();
                    if (playerName == null) {
                        ArrayList<String> randomNames = new ArrayList<>();
                        randomNames.add("NotSoJuicyJuan");
                        randomNames.add("Notch");
                        randomNames.add("Availer");
                        randomNames.add("Vaquxine");
                        randomNames.add("Taco");
                        randomNames.add("ish");
                        playerName = randomNames.get((int) (Math.random() * randomNames.size()));
                    }
                    Gson gson = new Gson();

                    // Define the type of the HashMap
                    Type type = new com.google.gson.reflect.TypeToken<HashMap<Integer, Double>>() {
                    }.getType();
                    String jsonContributions = (String) obj.get(uuid.toString());
                    playerMap.put(uuid, new PlayerData(playerName, uuid, gson.fromJson(jsonContributions, type)));
                }

                QuestTypeHandler handler = new QuestTypeHandler(questType);
                QuestModel model = plugin.config().getQuestLibrary().getQuestModelById(questId);

                if (model == null) {
                    Bukkit.getLogger().info("The quest in the save file has expired and will not be initialized.");
                    continue;
                }
                QuestController controller = handler.createControllerFromSave(model, playerMap,
                        (int) questDuration, objectives);
                activeQuests.beginQuestFromSave(controller);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
