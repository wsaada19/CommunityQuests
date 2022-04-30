package me.wonka01.ServerQuests;

import lombok.Getter;
import lombok.NonNull;
import me.knighthat.apis.files.Config;
import me.knighthat.apis.files.Messages;
import me.wonka01.ServerQuests.commands.CommunityQuestsCommands;
import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.configuration.messages.LanguageConfig;
import me.wonka01.ServerQuests.events.questevents.*;
import me.wonka01.ServerQuests.gui.*;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import me.wonka01.ServerQuests.questcomponents.QuestBar;
import me.wonka01.ServerQuests.questcomponents.players.BasePlayerComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerQuests extends JavaPlugin {

    public static Economy economy = null;

    @Getter
    private final @NonNull Config newConfig = new Config(this);
    @Getter
    private final @NonNull Messages messages = new Messages(this);
    public QuestLibrary questLibrary;
    private StartGui startGui;
    private StopGui stopGui;
    private DonateQuestGui questGui;
    private ViewGui viewGui;
    private DonateOptions donateOptionsGui;
    private ActiveQuests activeQuests;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        getLogger().info("Plugin is enabled");
        CommunityQuestsCommands commandExecutor = new CommunityQuestsCommands();
        commandExecutor.setup(this);

        loadConfig();
        loadConfigurationLimits();
        loadQuestLibraryFromConfig();
        loadSaveData();
        LanguageConfig.getConfig().setUpLanguageConfig();

        if (!setupEconomy()) {
            getLogger().info("Warning! No economy plugin found, a cash reward can not be added to a quest in Community Quests.");
        }

        loadGuis();
        registerEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled");
        jsonSave.saveQuestsInProgress();
        BarManager.closeBar();
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
        String barColor = getConfig().getString("barColor");
        int leaderBoardLimit = getConfig().getInt("leaderBoardSize", 5);
        boolean disableBossBar = getConfig().getBoolean("disableBossBar", false);
        BarManager.setDisableBossBar(disableBossBar);
        BasePlayerComponent.setLeaderBoardSize(leaderBoardLimit);
        QuestBar.barColor = barColor;
        ActiveQuests.setQuestLimit(questLimit);
    }

    private void loadGuis() {
        TypeGui typeGui = new TypeGui(this);
        typeGui.initializeItems();
        getServer().getPluginManager().registerEvents(typeGui, this);
        viewGui = new ViewGui(this);
        startGui = new StartGui(this, typeGui);
        startGui.initializeItems();
        stopGui = new StopGui(this);
        questGui = new DonateQuestGui(this);
        questGui.initializeItems();
        donateOptionsGui = new DonateOptions(this, questGui);
    }

    public void reloadConfiguration() {
        reloadConfig();
        saveConfig();
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("Quests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        loadConfigurationLimits();
        LanguageConfig.getConfig().setUpLanguageConfig();
        loadGuis();
    }

    public QuestLibrary getQuestLibrary() {
        return questLibrary;
    }

    public StartGui getStartGui() {
        return startGui;
    }

    public StopGui getStopGui() {
        return stopGui;
    }

    public DonateQuestGui getQuestsGui() {
        return questGui;
    }

    public DonateOptions getDonateOptionsGui() {
        return donateOptionsGui;
    }

    public ViewGui getViewGui() {
        return viewGui;
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

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(questGui, this);
        getServer().getPluginManager().registerEvents(startGui, this);
        getServer().getPluginManager().registerEvents(stopGui, this);
        getServer().getPluginManager().registerEvents(viewGui, this);
        getServer().getPluginManager().registerEvents(donateOptionsGui, this);
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
}
