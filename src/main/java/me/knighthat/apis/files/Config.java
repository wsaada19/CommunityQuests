package me.knighthat.apis.files;

import lombok.Getter;
import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.gui.DonateMenu;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.bossbar.BarManager;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;

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
        BasePlayerComponent.setLeaderBoardSize(leaderBoardSize);

        boolean disableBar = get().getBoolean("disableBossBar", false);
        BarManager.setDisableBossBar(disableBar);

        Material borderMaterial = Material.matchMaterial(get().getString("donateMenuItem", "BLACK_STAINED_GLASS_PANE"));
        if (borderMaterial == null) {
            borderMaterial = Material.BLACK_STAINED_GLASS_PANE;
        }
        DonateMenu.setBorderItem(new ItemStack(borderMaterial));
    }

    public void initializeQuests() {
        ConfigurationSection quests = get().getConfigurationSection("Quests");
        this.questLibrary.loadQuestConfiguration(quests);
    }
}
