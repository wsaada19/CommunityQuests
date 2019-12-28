package me.wonka01.ServerQuests.configuration;

import me.wonka01.ServerQuests.handlers.EventTypeHandler;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.CompetitiveQuestData;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonQuestSave {
    private File path;
    private ActiveQuests activeQuests;

    public JsonQuestSave(File path, ActiveQuests activeQuests){
        this.path = new File(path + "/questSave.json");
        this.activeQuests = activeQuests;
    }

    public void getOrCreateQuestFile(){
        if(path.exists()){
            Bukkit.getServer().getConsoleSender().sendMessage("The quest file exists!");
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage("The quest file does not exist!");
            try {
                path.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
            }
        }
    }

    public void saveQuestsInProgress(){
        JSONArray jsonArray = new JSONArray();
        for(QuestController questController : activeQuests.getActiveQuestsList()){
            JSONObject jObject = new JSONObject();
            jObject.put("id", questController.getQuestType());
            jObject.put("playerMap", questController.getPlayerComponent().getPlayerDataInJson());
            jObject.put("amountComplete", questController.getQuestData().getAmountCompleted());
            if(questController.getQuestData() instanceof CompetitiveQuestData){
                jObject.put("type", "comp");
            } else {
                jObject.put("type", "coop");
            }
            jsonArray.add(jObject);
        }
        try {
            FileWriter fileWriter = new FileWriter(path);
            JSONObject object = new JSONObject();
            object.put("activeQuests", jsonArray);
            fileWriter.write(object.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readAndInitializeQuests(){
        if(!path.exists()){return;}

        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader(path.getPath()));
            JSONArray questArray = (JSONArray) object.get("activeQuests");
            Iterator qIterator = questArray.iterator();

            while (qIterator.hasNext()){
                JSONObject questObject = (JSONObject) qIterator.next();
                String questId = (String)questObject.get("id");
                String questType = (String)questObject.get("type");
                long amountComplete = (Long) questObject.get("amountComplete");

                JSONArray playerObject = (JSONArray) questObject.get("playerMap");
                Iterator<JSONObject> pIterator = playerObject.iterator();
                Map<UUID, PlayerData> playerMap = new TreeMap<UUID, PlayerData>();
                while(pIterator.hasNext()){
                    JSONObject obj = pIterator.next();
                    UUID uuid = UUID.fromString((String)obj.keySet().iterator().next());
                    String playerName = Bukkit.getServer().getOfflinePlayer(uuid).getName();
                    long pContributed = (Long) obj.get(uuid.toString());
                    playerMap.put(uuid, new PlayerData(playerName, (int)pContributed));
                }

                EventTypeHandler handler = new EventTypeHandler(questType);
                QuestModel model = QuestLibrary.getQuestLibraryInstance().getQuestModelById(questId);
                if(model == null){
                    continue;
                }
                Bukkit.getServer().getConsoleSender().sendMessage("" + (int)amountComplete);

                QuestController controller = handler.crateControllerFromSave(model, playerMap, (int)amountComplete);
                activeQuests.startQuestWithController(controller);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /*public void jsonStuff()
    {
        JSONParser jsonParser = new JSONParser();
        Object parsed = jsonParser.parse(new FileReader(file.getPath()));
        JSONObject jsonObject = (JSONObject) parsed;
    }*/
}
