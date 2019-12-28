package me.wonka01.ServerQuests;

import me.wonka01.ServerQuests.configuration.JsonQuestSave;
import me.wonka01.ServerQuests.events.*;
import me.wonka01.ServerQuests.events.questevents.*;
import me.wonka01.ServerQuests.gui.EventTypeGui;
import me.wonka01.ServerQuests.gui.StopEventGui;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import org.bukkit.Bukkit;
import me.wonka01.ServerQuests.commands.EventsCommands;
import me.wonka01.ServerQuests.configuration.QuestLibrary;
import me.wonka01.ServerQuests.gui.StartEventGui;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class ServerQuests extends JavaPlugin {

    public static Economy economy = null;
    public QuestLibrary questLibrary;
    private EventsCommands commandExecutor;
    private StartEventGui startEventGui;
    private StopEventGui stopEventGui;
    private ActiveQuests activeQuests;
    private JsonQuestSave jsonSave;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        commandExecutor = new EventsCommands();
        commandExecutor.setup();

        loadConfig();
        loadQuestLibraryFromConfig();

        if(!setupEconomy())
        {
            this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Warning! No economy api found, a cash reward can not be added" +
                    " to a quest.");
        }

        loadStartEventGui();
        registerEvents();
        getServer().getPluginManager().registerEvents(startEventGui, this);
        StopEventGui stopEventGui = new StopEventGui();
        getServer().getPluginManager().registerEvents(stopEventGui, this);
    }

    @Override
    public void onDisable() {

        getLogger().info("onDisable is called!");
        jsonSave.saveQuestsInProgress();
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadQuestLibraryFromConfig()
    {
        ConfigurationSection serverQuestSection = getConfig().getConfigurationSection("ServerQuests");
        questLibrary = new QuestLibrary();
        questLibrary.loadQuestConfiguration(serverQuestSection);
        ActiveQuests activeQuests = new ActiveQuests();
        this.activeQuests = activeQuests;
        JsonQuestSave saveJson = new JsonQuestSave(getDataFolder(), activeQuests);
        jsonSave = saveJson;
        jsonSave.getOrCreateQuestFile();
        jsonSave.readAndInitializeQuests();
    }

    private void loadStartEventGui()
    {
        EventTypeGui eventTypeGui = new EventTypeGui();
        eventTypeGui.initializeItems();
        getServer().getPluginManager().registerEvents(eventTypeGui, this);
        StartEventGui startGui = new StartEventGui(eventTypeGui);
        startGui.initializeItems();
        startEventGui = startGui;
    }

    public StartEventGui getStartGui()
    {
        return startEventGui;
    }
    public StopEventGui getStopGui()
    {
        return stopEventGui;
    }
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            Bukkit.getServer().getConsoleSender().sendMessage(economy.currencyNameSingular());
        }
        return (economy != null);
    }

    private void registerEvents()
    {
        BreakEvent blockBreakEvent = new BreakEvent(activeQuests);
        CatchFishEvent catchFishEvent = new CatchFishEvent(activeQuests);
        KillPlayerEvent killEvent = new KillPlayerEvent(activeQuests);
        MobKillEvent mobKillEvent = new MobKillEvent(activeQuests);
        ProjectileKillEvent projectileKillEvent = new ProjectileKillEvent(activeQuests);
        PlaceEvent placeEvent = new PlaceEvent(activeQuests);
        ShearEvent shearEvent = new ShearEvent( activeQuests);
        TameEvent tameEvent = new TameEvent(activeQuests);

        getServer().getPluginManager().registerEvents(startEventGui, this);
        getServer().getPluginManager().registerEvents(blockBreakEvent, this);
        getServer().getPluginManager().registerEvents(catchFishEvent, this);
        getServer().getPluginManager().registerEvents(killEvent, this);
        getServer().getPluginManager().registerEvents(mobKillEvent, this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(projectileKillEvent, this);
        getServer().getPluginManager().registerEvents(placeEvent, this);
        getServer().getPluginManager().registerEvents(shearEvent, this);
        getServer().getPluginManager().registerEvents(tameEvent, this);

    }


}