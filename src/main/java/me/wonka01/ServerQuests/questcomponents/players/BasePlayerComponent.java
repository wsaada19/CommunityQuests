package me.wonka01.ServerQuests.questcomponents.players;

import me.wonka01.ServerQuests.questcomponents.rewards.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class BasePlayerComponent {

    protected Map<UUID, PlayerData> playerMap;
    protected ArrayList<Reward> rewardsList;

    public BasePlayerComponent(ArrayList<Reward> rewardsList)
    {
        this.rewardsList = rewardsList;
        this.playerMap = new TreeMap<UUID, PlayerData>();
    }

    public BasePlayerComponent(ArrayList<Reward> rewardsList, Map<UUID, PlayerData> map){
        this.rewardsList = rewardsList;
        this.playerMap = map;
    }

    public void savePlayerAction(Player player, int count)
    {
        if(playerMap.containsKey(player.getUniqueId())){
            PlayerData playerData = playerMap.get(player.getUniqueId());
            playerData.increaseContribution(count);
        } else {
            PlayerData playerData = new PlayerData(player.getDisplayName());
            playerData.increaseContribution(count);

            playerMap.put(player.getUniqueId(), playerData);
        }
    }

    public String getLeaderString()
    {
        String result = "";
        for(UUID key : playerMap.keySet())
        {
            result += ChatColor.GREEN + Bukkit.getServer().getPlayer(key).getDisplayName() + ": " + ChatColor.WHITE + playerMap.get(key).getAmountContributed();
        }
        return result;
    }

    public PlayerData getTopPlayerData()
    {
        for(UUID key : playerMap.keySet())
        {
            return playerMap.get(key);
        }

        return null;
    }

    public int getAmountContributed(Player player){
        if(playerMap.containsKey(player.getUniqueId())){
            return playerMap.get(player.getUniqueId()).getAmountContributed();
        }

        return 0;
    }

    public JSONArray getPlayerDataInJson(){
        JSONArray jArray = new JSONArray();
        for(UUID key : playerMap.keySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key.toString(), playerMap.get(key).getAmountContributed());
            jArray.add(jsonObject);
        }
        return  jArray;
    }

    public void sendMessageToPlayers(String victoryMessage)
    {
        for(UUID key : playerMap.keySet())
        {
            Player player = Bukkit.getServer().getPlayer(key);
            if(player.isOnline())
            {
                player.sendMessage(victoryMessage);
            }
        }
    }

    public void giveOutRewards(int questGoal)
    {
        for(UUID key : playerMap.keySet())
        {
            double playerContributionRatio = (double)playerMap.get(key).getAmountContributed() / (double)questGoal;
            for (Reward reward : rewardsList) {
                reward.giveRewardToPlayer(Bukkit.getServer().getOfflinePlayer(key), playerContributionRatio);
            }
        }
    }
}
