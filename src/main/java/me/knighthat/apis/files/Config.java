package me.knighthat.apis.files;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public final class Config extends Getters {

    private final @NonNull QuestLibrary questLibrary = new QuestLibrary();
    private final @NonNull ActiveQuests activeQuests = new ActiveQuests();

    public Config(ServerQuests plugin) {

        super(plugin);
        initializeVariables();
        initializeQuests();
    }

    public void initializeVariables() {

        int limit = get().getInt("questLimit", 5);
        ActiveQuests.setQuestLimit(limit);

        int leaderBoardSize = get().getInt("leaderBoardSize", 5);
        BasePlayerComponent.setLeaderBoardSize(leaderBoardSize);

        boolean disableBar = get().getBoolean("disableBossBar", false);
        BarManager.setDisableBossBar(disableBar);
    }

    public void initializeQuests() {

        ConfigurationSection quests = get().getConfigurationSection("Quests");
        this.questLibrary.loadQuestConfiguration(quests);
    }
}
