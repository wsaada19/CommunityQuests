package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import me.wonka01.ServerQuests.util.EventTypeHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class JsonQuestSave {
    private File path;
    private ActiveQuests activeQuests;

    public JsonQuestSave(File path, ActiveQuests activeQuests) {
        this.path = new File(path + "/questSave.json");
        this.activeQuests = activeQuests;
    }

    public boolean getOrCreateQuestFile() {
        if (path.exists()) {
            return true;
        } else {
            try {
                path.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
            }
        }
        return false;
    }

    public void saveQuestsInProgress() {
        JSONArray jsonArray = new JSONArray();
        for (QuestController questController : activeQuests.getActiveQuestsList()) {

            if(questController.getQuestData().isQuestComplete()) {
                continue;
            }

            JSONObject jObject = new JSONObject();
            jObject.put("id", questController.getQuestType());
            jObject.put("playerMap", questController.getPlayerComponent().getPlayerDataInJson());
            jObject.put("amountComplete", questController.getQuestData().getAmountCompleted());
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

    public void readAndInitializeQuests() {
        if (!path.exists()) {
            return;
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader(path.getPath()));
            JSONArray questArray = (JSONArray) object.get("activeQuests");
            Iterator qIterator = questArray.iterator();

            while (qIterator.hasNext()) {
                JSONObject questObject = (JSONObject) qIterator.next();
                String questId = (String) questObject.get("id");
                String questType = (String) questObject.get("type");
                long amountComplete = (Long) questObject.get("amountComplete");

                JSONArray playerObject = (JSONArray) questObject.get("playerMap");
                Iterator<JSONObject> pIterator = playerObject.iterator();
                Map<UUID, PlayerData> playerMap = new TreeMap<UUID, PlayerData>();
                while (pIterator.hasNext()) {
                    JSONObject obj = pIterator.next();
                    UUID uuid = UUID.fromString((String) obj.keySet().iterator().next());
                    String playerName = Bukkit.getServer().getOfflinePlayer(uuid).getName();
                    long pContributed = (Long) obj.get(uuid.toString());
                    playerMap.put(uuid, new PlayerData(playerName, (int) pContributed));
                }

                EventTypeHandler handler = new EventTypeHandler(questType);
                QuestModel model = JavaPlugin.getPlugin(ServerQuests.class).getQuestLibrary().getQuestModelById(questId);
                if (model == null) {
                    continue;
                }
                if (amountComplete >= model.getQuestGoal()) {
                    Bukkit.getLogger().info("The quest in the save file has expired and will not be initialized.");
                    break;
                }
                QuestController controller = handler.createControllerFromSave(model, playerMap, (int) amountComplete);
                activeQuests.startQuestWithController(controller);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
