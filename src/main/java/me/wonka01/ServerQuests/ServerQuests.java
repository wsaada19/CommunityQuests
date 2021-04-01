package me.wonka01.ServerQuests;

import me.wonka01.ServerQuests.commands.ServerQuestsCommands;
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
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerQuests extends JavaPlugin {

    public static Economy economy = null;
    public QuestLibrary questLibrary;
    private ServerQuestsCommands commandExecutor;
    private StartGui startGui;
    private StopGui stopGui;
    private DonateQuestGui questGui;
    private ViewGui viewGui;

    private ActiveQuests activeQuests;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        getLogger().info("Plugin is enabled");
        commandExecutor = new ServerQuestsCommands();
        commandExecutor.setup();

        loadConfig();
        loadConfigurationLimits();
        loadQuestLibraryFromConfig();
        loadSaveData();
        LanguageConfig.getConfig().setUpLanguageConfig();

        if (!setupEconomy()) {
            getLogger().info("Warning! No economy plugin found, a cash reward can not be added to a quest.");
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

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadSaveData() {
        jsonSave = new JsonQuestSave(getDataFolder(), activeQuests);
        if (jsonSave.getOrCreateQuestFile()) {
            jsonSave.readAndInitializeQuests();
            BarManager.initializeDisplayBar();
        }
    }

    public void loadQuestLibraryFromConfig() {
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("Quests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        this.activeQuests = new ActiveQuests();
    }

    private void loadConfigurationLimits() {
        int questLimit = getConfig().getInt("questLimit");
        String barColor = getConfig().getString("barColor");
        int leaderBoardLimit = getConfig().getInt("leaderBoardSize");
        BasePlayerComponent.setLeaderBoardSize(leaderBoardLimit);
        QuestBar.barColor = barColor;
        ActiveQuests.setQuestLimit(questLimit);
    }

    private void loadGuis() {
        TypeGui typeGui = new TypeGui();
        typeGui.initializeItems();
        getServer().getPluginManager().registerEvents(typeGui, this);
        viewGui = new ViewGui();
        startGui = new StartGui(typeGui);
        startGui.initializeItems();
        stopGui = new StopGui();
        questGui = new DonateQuestGui();
        questGui.initializeItems();
    }

    public void reloadConfiguration() {
        reloadConfig();
        saveConfig();
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("Quests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        LanguageConfig.getConfig().setUpLanguageConfig();
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

    public ViewGui getViewGui() {return viewGui;}

    private boolean setupEconomy() {
        try {
            Class.forName("net.milkbowl.vault.economy.Economy");
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                economy = economyProvider.getProvider();
                Bukkit.getServer().getConsoleSender().sendMessage(economy.currencyNameSingular());
            }
            return (economy != null);
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(questGui, this);
        getServer().getPluginManager().registerEvents(startGui, this);
        getServer().getPluginManager().registerEvents(stopGui, this);
        getServer().getPluginManager().registerEvents(viewGui, this);
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
    }
}