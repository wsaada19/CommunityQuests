package me.wonka01.ServerQuests;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.files.Config;
import me.knighthat.apis.files.Messages;
import me.knighthat.apis.menus.MenuEvents;
import me.wonka01.ServerQuests.commands.CommandManager;
import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.events.*;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import me.wonka01.ServerQuests.questcomponents.QuestBar;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class ServerQuests extends JavaPlugin {


    @Getter
    private final @NonNull Config newConfig = new Config(this);
    @Getter
    private final @NonNull Messages messages = new Messages(this);
    public QuestLibrary questLibrary;
    @Getter
    private Economy economy;
    private ActiveQuests activeQuests;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {

        new CommandManager(this);

        loadConfig();
        loadConfigurationLimits();
        loadQuestLibraryFromConfig();
        loadSaveData();

        if (!setupEconomy()) {
            getLogger().info("Warning! No economy plugin found, a cash reward can not be added to a quest in Community Quests.");
        }

        registerGuiEvents();
        registerQuestEvents();

        getLogger().info("Plugin is enabled");
    }

    @Override
    public void onDisable() {

        jsonSave.saveQuestsInProgress();
        BarManager.closeBar();

        getLogger().info("Plugin is disabled");
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadSaveData() {
        jsonSave = new JsonQuestSave(getDataFolder(), activeQuests);
        if (jsonSave.getOrCreateQuestFile()) {
            jsonSave.readAndInitializeQuests();
            BarManager.initializeDisplayBar();
        }
    }

    private void loadQuestLibraryFromConfig() {
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("Quests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        this.activeQuests = new ActiveQuests();
    }

    private void loadConfigurationLimits() {
        int questLimit = getConfig().getInt("questLimit");
        String barColor = getConfig().getString("barColor").toUpperCase(Locale.ROOT);
        int leaderBoardLimit = getConfig().getInt("leaderBoardSize", 5);
        boolean disableBossBar = getConfig().getBoolean("disableBossBar", false);
        BarManager.setDisableBossBar(disableBossBar);
        BasePlayerComponent.setLeaderBoardSize(leaderBoardLimit);
        QuestBar.barColor = barColor;
        ActiveQuests.setQuestLimit(questLimit);
    }

    public void reloadConfiguration() {
        reloadConfig();
        saveConfig();
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("Quests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        loadConfigurationLimits();
        messages.reload();
        registerGuiEvents();
    }

    public QuestLibrary getQuestLibrary() {
        return questLibrary;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private void registerQuestEvents() {
        getServer().getPluginManager().registerEvents(new BarManager(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new CatchFishEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new KillPlayerEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new MobKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ProjectileKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ShearEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new TameEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new MilkCowEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new CraftItemQuestEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ConsumeItemQuestEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new EnchantItemQuestEvent(activeQuests), this);
    }

    private void registerGuiEvents() {

        getServer().getPluginManager().registerEvents(new MenuEvents(), this);
    }
}
