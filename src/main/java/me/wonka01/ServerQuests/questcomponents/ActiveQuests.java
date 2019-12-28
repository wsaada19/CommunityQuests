package me.wonka01.ServerQuests.questcomponents;

import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.handlers.EventTypeHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveQuests {

    private static ActiveQuests activeQuestsInstance;

    private List<QuestController> activeQuestsList;

    public ActiveQuests()
    {
        activeQuestsList = new ArrayList<QuestController>();
        activeQuestsInstance = this;
    }

    public void endQuest(UUID questId)
    {
        for(int i =0; i < activeQuestsList.size(); i++)
        {
            if(activeQuestsList.get(i).getQuestId().equals(questId)){
                activeQuestsList.remove(i);
            }
        }

    }

    public void InitializeQuestListener(QuestModel questModel, EventTypeHandler.EventType eventType)
    {
        EventTypeHandler typeHandler = new EventTypeHandler(eventType);
        QuestController controller = typeHandler.createQuestController(questModel);

        activeQuestsList.add(controller);

    }

    public void startQuestWithController(QuestController controller){
        activeQuestsList.add(controller);
    }

    public List<QuestController> getActiveQuestsList() {
        if(activeQuestsList == null){
            activeQuestsList = new ArrayList<QuestController>();
        }
        return activeQuestsList;
    }


    public static ActiveQuests getActiveQuestsInstance()
    {
        return activeQuestsInstance;
    }

    public void showBossBar(Player player)
    {

    }

    public void hideBossBar(Player player)
    {

    }

}
