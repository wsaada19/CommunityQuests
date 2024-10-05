package me.wonka01.ServerQuests.questcomponents;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestModel;
import me.wonka01.ServerQuests.enums.EventType;
import me.wonka01.ServerQuests.objectives.Objective;
import me.wonka01.ServerQuests.questcomponents.bossbar.QuestBar;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import me.wonka01.ServerQuests.questcomponents.players.PlayerData;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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
        return createController(model, null, model.getCompleteTime(), model.getObjectives());
    }

    public QuestController createControllerFromSave(@NonNull QuestModel model, @NonNull Map<UUID, PlayerData> players,
            int timeLeft, List<Objective> objectives) {
        return createController(model, players, timeLeft, objectives);
    }

    private @NonNull QuestController createController(@NonNull QuestModel model,
            @Nullable Map<UUID, PlayerData> players, int timeLeft, List<Objective> objectives) {
        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);

        String barColor = model.getBarColor();
        if (barColor == null || barColor.isEmpty()) {
            barColor = plugin.getConfig().getString("barColor", "GREEN");
        }

        QuestBar bar = new QuestBar(model.getDisplayName(), barColor);

        BasePlayerComponent pComponent = new BasePlayerComponent(model.getRewards(), model.getRewardLimit(),
                model.getRankedRewards());

        if (players != null) {
            pComponent = new BasePlayerComponent(model.getRewards(), players, model.getRewardLimit(),
                    model.getRankedRewards());
        }

        List<Objective> objs = new ArrayList<Objective>();
        for (Objective obj : objectives) {
            objs.add(obj.clone());
        }

        QuestData data = getQuestData(model, pComponent, timeLeft, objs);
        EventConstraints event = new EventConstraints(model.getWorlds());

        if (data.getAmountCompleted() > 0) {
            bar.updateBarProgress(data.getAmountCompleted() / data.getQuestGoal());
        }

        return new QuestController(plugin, data, bar, pComponent, event);
    }

    private QuestData getQuestData(QuestModel questModel, BasePlayerComponent playerComponent,
            int timeLeft, List<Objective> objectives) {
        if (eventType == EventType.COMPETITIVE) {
            return new CompetitiveQuestData(questModel.getDisplayName(),
                    questModel.getEventDescription(), playerComponent, questModel.getQuestId(),
                    timeLeft, questModel.getDisplayItem(), questModel.getQuestId(), questModel.getAfterQuestCommand(),
                    questModel.getBeforeQuestCommand(), objectives, questModel.getQuestFailedCommand());
        } else {
            return new QuestData(questModel.getDisplayName(),
                    questModel.getEventDescription(), questModel.getQuestId(), timeLeft,
                    questModel.getDisplayItem(), questModel.getQuestId(), questModel.getAfterQuestCommand(),
                    questModel.getBeforeQuestCommand(), objectives, questModel.getQuestFailedCommand());
        }
    }
}
