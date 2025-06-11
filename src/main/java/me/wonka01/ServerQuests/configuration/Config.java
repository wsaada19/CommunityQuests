package me.wonka01.ServerQuests.configuration;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.files.Getters;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.events.BreakEvent;
import me.wonka01.ServerQuests.events.PlaceEvent;
import me.wonka01.ServerQuests.gui.DonateMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
import me.wonka01.ServerQuests.questcomponents.players.PlayerContributionMap;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

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
        PlayerContributionMap.setLeaderBoardSize(leaderBoardSize);

        boolean disableBar = get().getBoolean("disableBossBar", false);
        BarManager.setDisableBossBar(disableBar);

        Material borderMaterial = Material.matchMaterial(get().getString("donateMenuItem", "BLACK_STAINED_GLASS_PANE"));
        if (borderMaterial == null) {
            borderMaterial = Material.BLACK_STAINED_GLASS_PANE;
        }
        DonateMenu.setBorderItem(new ItemStack(borderMaterial));

        PlaceEvent.setDisableDuplicatePlaces(get().getBoolean("disableDuplicatePlaces", false));
        BreakEvent.setDisableDuplicateBreaks(get().getBoolean("disableDuplicateBreaks", false));
    }

    public void initializeQuests() {
        ConfigurationSection quests = get().getConfigurationSection("Quests");
        this.questLibrary.loadQuestConfiguration(quests);
    }
}
