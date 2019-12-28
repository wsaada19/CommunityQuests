package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.handlers.EventListenerHandler;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuestController {

    private QuestBar questBar;
    private QuestData questData;
    private BasePlayerComponent playerComponent;
    private EventConstraints eventConstraints;
    private UUID questId;
    private EventListenerHandler.EventListenerType listenerType;

    public QuestController(QuestData questData, QuestBar questBar,
                           BasePlayerComponent playerComponent, EventConstraints eventConstraints,
                           EventListenerHandler.EventListenerType listenerType)
    {
        this.questData = questData;
        this.questBar = questBar;
        this.playerComponent = playerComponent;
        questId = UUID.randomUUID();
        this.eventConstraints = eventConstraints;
        this.listenerType = listenerType;
    }

    public boolean updateQuest(int count, Player player)
    {
        questData.addToQuestProgress(count);

        playerComponent.savePlayerAction(player, count);

        updateBossBar();

        return questData.isQuestComplete();
    }

    public void updateBossBar()
    {
        double barProgress = questData.getPercentageComplete();
        questBar.updateBarProgress(barProgress);
    }

    public void handleQuestComplete()
    {
        questBar.removeBossBar();
        Bukkit.getServer().broadcastMessage(playerComponent.getLeaderString());
        playerComponent.giveOutRewards(questData.getQuestGoal());
        sendVictoryMessage();
        ActiveQuests.getActiveQuestsInstance().endQuest(questId);
    }

    public void sendVictoryMessage(){
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Quest Complete!");
    }

    public void showBossBar(Player player)
    {
        questBar.showBossBar(player);
    }

    public void hideBossBar(Player player)
    {
        questBar.hideBossBar(player);
    }

    public UUID getQuestId(){return questId;}

    public QuestData getQuestData(){
        return questData;
    }

    public BasePlayerComponent getPlayerComponent(){
        return playerComponent;
    }
    public EventConstraints getEventConstraints()
    {
        return eventConstraints;
    }

    public EventListenerHandler.EventListenerType getListenerType() {
        return listenerType;
    }

    public boolean isCompetitive(){
        return (questData instanceof CompetitiveQuestData);
    }

    public String getQuestType(){return questData.getQuestType();}
}
