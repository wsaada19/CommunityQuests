package me.wonka01.ServerQuests.questcomponents;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.questcomponents.bossbar.QuestBar;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class QuestTypeHandler {
    private final EventType eventType;

    public QuestTypeHandler(EventType eventType) {
        this.eventType = eventType;
    }

    public QuestTypeHandler(String type) {
        eventType = type.equalsIgnoreCase("comp") ? EventType.COMPETITIVE : EventType.COLLAB;
    }

    public QuestController createQuestController(@NonNull QuestModel model) {
        return createController(model, null, 0, model.getCompleteTime());
    }

    public QuestController createControllerFromSave(@NonNull QuestModel model, @NonNull Map<UUID, PlayerData> players,
                                                    int completed, int timeLeft) {
        return createController(model, players, completed, timeLeft);
    }

    private @NonNull QuestController createController(@NonNull QuestModel model, @Nullable Map<UUID, PlayerData> players, int completed, int timeLeft) {
        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);

        QuestBar bar = new QuestBar(model.getDisplayName(), plugin.getConfig().getString("barColor", ""));

        if (completed > 0) {
            bar.updateBarProgress((double) completed / model.getQuestGoal());
        }

        BasePlayerComponent pComponent = new BasePlayerComponent(model.getRewards());
        if (players != null) {
            pComponent = new BasePlayerComponent(model.getRewards(), players);
        }

        QuestData data = getQuestData(model, completed, pComponent, timeLeft);
        EventConstraints event = new EventConstraints(model.getItemNames(), model.getMobNames(), model.getWorlds());

        return new QuestController(plugin, data, bar, pComponent, event, model.getObjective());
    }

    private QuestData getQuestData(QuestModel questModel, int amountComplete, BasePlayerComponent playerComponent, int timeLeft) {
        if (eventType == EventType.COMPETITIVE) {
            return new CompetitiveQuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                questModel.getEventDescription(), playerComponent, questModel.getQuestId(), amountComplete, timeLeft, questModel.getDisplayItem());
        } else {
            return new QuestData(questModel.getQuestGoal(), questModel.getDisplayName(),
                questModel.getEventDescription(), questModel.getQuestId(), amountComplete, timeLeft, questModel.getDisplayItem());
        }
    }
}
