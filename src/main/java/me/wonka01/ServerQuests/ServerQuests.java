package me.wonka01.ServerQuests;

import me.wonka01.ServerQuests.commands.ServerQuestsCommands;
import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.events.questevents.*;
import me.wonka01.ServerQuests.gui.StartGui;
import me.wonka01.ServerQuests.gui.StopGui;
import me.wonka01.ServerQuests.gui.TypeGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.BarManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerQuests extends JavaPlugin {

    private final String ServerQuests = "[ServerQuests]";

    public static Economy economy = null;
    public QuestLibrary questLibrary;
    private ServerQuestsCommands commandExecutor;
    private StartGui startGui;
    private StopGui stopGui;
    private ActiveQuests activeQuests;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        getLogger().info(ServerQuests + " Plugin is enabled");
        commandExecutor = new ServerQuestsCommands();
        commandExecutor.setup();

        loadConfig();
        loadQuestLibraryFromConfig();
        loadConfigurationLimits();
        loadSaveData();

        if (!setupEconomy()) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.RED + ServerQuests + " Warning! No economy plugin found, a cash reward can not be added" +
                    " to a quest.");
        }

        loadStartEventGui();
        registerEvents();
        getServer().getPluginManager().registerEvents(startGui, this);
        StopGui stopGui = new StopGui();
        getServer().getPluginManager().registerEvents(stopGui, this);
    }

    @Override
    public void onDisable() {
        getLogger().info(ServerQuests + " Plugin is disabled");
        jsonSave.saveQuestsInProgress();
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadSaveData() {
        jsonSave = new JsonQuestSave(getDataFolder(), activeQuests);
        if (jsonSave.getOrCreateQuestFile()) {
            jsonSave.readAndInitializeQuests();
        }
    }

    public void loadQuestLibraryFromConfig() {
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("ServerQuests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        this.activeQuests = new ActiveQuests();
    }

    private void loadConfigurationLimits() {
        int questLimit = getConfig().getInt("questLimit");
        ActiveQuests.setQuestLimit(questLimit);
    }

    private void loadStartEventGui() {
        TypeGui typeGui = new TypeGui();
        typeGui.initializeItems();
        getServer().getPluginManager().registerEvents(typeGui, this);
        StartGui startGui = new StartGui(typeGui);
        startGui.initializeItems();
        this.startGui = startGui;
        stopGui = new StopGui();
    }

    public void reloadConfiguration() {
        reloadConfig();
        saveConfig();
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("ServerQuests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
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
        getServer().getPluginManager().registerEvents(startGui, this);
        getServer().getPluginManager().registerEvents(stopGui, this);
        getServer().getPluginManager().registerEvents(new BarManager(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new CatchFishEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new KillPlayerEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new MobKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ProjectileKillEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new ShearEvent(activeQuests), this);
        getServer().getPluginManager().registerEvents(new TameEvent(activeQuests), this);
    }
}