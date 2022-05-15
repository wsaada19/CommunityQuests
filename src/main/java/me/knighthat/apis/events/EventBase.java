package me.knighthat.apis.events;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.enums.ObjectiveType;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public abstract class EventBase<T extends Event> implements Listener {

    private final ServerQuests plugin;
    private final ObjectiveType objectiveType;

    protected EventBase(ServerQuests plugin, @NonNull ObjectiveType objectiveType) {

        this.plugin = plugin;
        this.objectiveType = objectiveType;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public abstract void event(T event);

    protected @NonNull ActiveQuests getActiveQuests() {
        return plugin.config().getActiveQuests();
    }

    protected @NonNull List<QuestController> getControllers() {

        Stream<QuestController> result = getActiveQuests().getActiveQuestsList().stream();
        return result.filter(ins -> ins.getObjective().equals(this.objectiveType)).collect(Collectors.toList());
    }

    protected void update(@NonNull QuestController controller, @NonNull Player player, double amount) {

        controller.updateQuest(amount, player);

        if (controller.getQuestData().isGoalComplete())
            controller.endQuest();
    }
}
