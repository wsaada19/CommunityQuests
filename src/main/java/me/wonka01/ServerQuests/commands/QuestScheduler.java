package me.wonka01.ServerQuests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestScheduler extends PluginCommand {
    private final ServerQuests plugin;
    private File scheduleFile;
    private FileConfiguration scheduleConfig;
    private final Map<String, BukkitRunnable> activeSchedules = new HashMap<>();

    public enum ScheduleType {
        DAILY,
        WEEKLY,
        CUSTOM_DAYS
    }

    public QuestScheduler(ServerQuests plugin) {
        super(plugin, false);
        this.plugin = plugin;
        this.scheduleFile = new File(plugin.getDataFolder(), "schedules.yml");
        loadSchedules();
    }

    @Override
    public @NonNull String getName() {
        return "schedule";
    }

    private void loadSchedules() {
        if (!scheduleFile.exists()) {
            plugin.saveResource("schedules.yml", false);
        }
        scheduleConfig = YamlConfiguration.loadConfiguration(scheduleFile);

        // Load existing schedules on server start
        for (String key : scheduleConfig.getKeys(false)) {
            String questId = scheduleConfig.getString(key + ".questId");
            String mode = scheduleConfig.getString(key + ".mode");
            String time = scheduleConfig.getString(key + ".time");
            String scheduleType = scheduleConfig.getString(key + ".scheduleType", "DAILY");
            int interval = scheduleConfig.getInt(key + ".interval", 1);
            String dayOfWeek = scheduleConfig.getString(key + ".dayOfWeek", "MONDAY");
            boolean enabled = scheduleConfig.getBoolean(key + ".enabled", true);

            if (enabled) {
                scheduleQuest(key, questId, mode, time, ScheduleType.valueOf(scheduleType), interval,
                        dayOfWeek);
            }
        }
    }

    private void saveSchedules() {
        try {
            scheduleConfig.save(scheduleFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save schedules.yml: " + e.getMessage());
        }
    }

    public void reloadSchedules() {
        // this.scheduleFile = new File(plugin.getDataFolder(), "schedules.yml");
        loadSchedules();
    }

    @Override
    public void execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length < 5) {
            sendUsageMessage(sender);
            return;
        }

        String questId = args[1];
        String mode = args[2].toLowerCase();
        String time = args[3];
        String scheduleTypeStr = args[4].toUpperCase();
        String action = args[5].toLowerCase();

        // check quest library to see if id exists
        if (!plugin.config().getQuestLibrary().containsQuest(questId) && !questId.equalsIgnoreCase("random")) {
            sender.sendMessage("§cQuest with ID " + questId + " does not exist!");
            return;
        }

        if (!mode.equals("coop") && !mode.equals("comp")) {
            sender.sendMessage("§cMode must be either 'coop' or 'comp'!");
            return;
        }

        ScheduleType scheduleType;
        try {
            scheduleType = ScheduleType.valueOf(scheduleTypeStr);
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cInvalid schedule type! Use DAILY, WEEKLY, or CUSTOM_DAYS");
            return;
        }

        try {
            DateTimeFormatter.ofPattern("HH:mm").parse(time);
        } catch (DateTimeParseException e) {
            sender.sendMessage("§cInvalid time format! Please use HH:mm (24-hour format)");
            return;
        }

        // Handle scheduling
        if (action.equals("add")) {
            int interval = 1;
            String dayOfWeek = "MONDAY";

            // Parse additional parameters based on schedule type
            if (scheduleType == ScheduleType.WEEKLY && args.length < 7) {
                sender.sendMessage("§cFor weekly schedules, specify the day of week: MONDAY, TUESDAY, etc.");
                return;
            } else if (scheduleType == ScheduleType.CUSTOM_DAYS && args.length < 7) {
                sender.sendMessage("§cFor custom day intervals, specify the number of days between runs");
                return;
            }

            if (scheduleType == ScheduleType.WEEKLY) {
                dayOfWeek = args[6].toUpperCase();
                try {
                    DayOfWeek.valueOf(dayOfWeek);
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("§cInvalid day of week! Use MONDAY, TUESDAY, etc.");
                    return;
                }
            } else if (scheduleType == ScheduleType.CUSTOM_DAYS) {
                try {
                    interval = Integer.parseInt(args[6]);
                    if (interval < 1) {
                        sender.sendMessage("§cInterval must be at least 1 day!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid interval! Please provide a number of days");
                    return;
                }
            }

            UUID scheduleId = UUID.randomUUID();

            // Save to config
            scheduleConfig.set(scheduleId.toString() + ".questId", questId);
            scheduleConfig.set(scheduleId.toString() + ".mode", mode);
            scheduleConfig.set(scheduleId.toString() + ".time", time);
            scheduleConfig.set(scheduleId.toString() + ".scheduleType", scheduleType.name());
            scheduleConfig.set(scheduleId.toString() + ".interval", interval);
            scheduleConfig.set(scheduleId.toString() + ".dayOfWeek", dayOfWeek);
            scheduleConfig.set(scheduleId.toString() + ".enabled", true);
            saveSchedules();

            // Schedule the quest
            scheduleQuest(scheduleId.toString(), questId, mode, time, scheduleType, interval, dayOfWeek);
            sender.sendMessage("§aQuest scheduled successfully! Schedule ID: " + scheduleId);

        } else if (action.equals("remove")) {
            if (args.length < 7) {
                sender.sendMessage("§cPlease provide the schedule ID to remove!");
                return;
            }

            String targetId = args[6];

            if (activeSchedules.containsKey(targetId)) {
                activeSchedules.get(targetId).cancel();
                activeSchedules.remove(targetId);
                scheduleConfig.set(targetId.toString(), null);
                saveSchedules();
                sender.sendMessage("§aSchedule removed successfully!");
            } else {
                sender.sendMessage("§cSchedule not found!");
            }
        } else {
            sender.sendMessage("§cInvalid action! Use 'add' or 'remove'");
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage("§cUsage:");
        sender.sendMessage(
                "§c/cq schedulequests <questId> <coop|comp> <time> <DAILY|WEEKLY|CUSTOM_DAYS> <add|remove> [day|interval]");
        sender.sendMessage("§cExamples:");
        sender.sendMessage("§c- Daily: /cq schedulequests quest1 coop 14:30 DAILY add");
        sender.sendMessage("§c- Weekly: /cq schedulequests quest1 coop 14:30 WEEKLY add MONDAY");
        sender.sendMessage("§c- Custom: /cq schedulequests quest1 coop 14:30 CUSTOM_DAYS add 3");
        sender.sendMessage("§c- Remove: /cq schedulequests quest1 coop 14:30 DAILY remove <scheduleId>");
    }

    private void scheduleQuest(String scheduleId, String questId, String mode, String time, ScheduleType scheduleType,
            int interval, String dayOfWeek) {
        // Parse the time string to get hours and minutes
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        // Create target time for today
        LocalDateTime targetTime = LocalDateTime.now()
                .withHour(hours)
                .withMinute(minutes)
                .withSecond(0)
                .withNano(0);

        // Adjust target time based on schedule type
        switch (scheduleType) {
            case WEEKLY:
                DayOfWeek targetDay = DayOfWeek.valueOf(dayOfWeek);
                while (targetTime.getDayOfWeek() != targetDay) {
                    targetTime = targetTime.plusDays(1);
                }
                break;
            case CUSTOM_DAYS:
                // If time today has passed, start from tomorrow
                if (targetTime.isBefore(LocalDateTime.now())) {
                    targetTime = targetTime.plusDays(1);
                }
                break;
            case DAILY:
                // If time today has passed, schedule for tomorrow
                if (targetTime.isBefore(LocalDateTime.now())) {
                    targetTime = targetTime.plusDays(1);
                }
                break;
        }

        // Calculate initial delay
        long initialDelayTicks = ChronoUnit.SECONDS.between(LocalDateTime.now(), targetTime) * 20;
        long intervalTicks;
        switch (scheduleType) {
            case DAILY:
                intervalTicks = 20 * 60 * 60 * 24; // 24 hours in ticks
                break;
            case WEEKLY:
                intervalTicks = 20 * 60 * 60 * 24 * 7; // 7 days in ticks
                break;
            case CUSTOM_DAYS:
                intervalTicks = 20 * 60 * 60 * 24 * interval; // interval days in ticks
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + scheduleType);
        }

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (questId.equalsIgnoreCase("random")) {
                    String random = plugin.config().getQuestLibrary().getRandomQuest();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cq start " + random + " " + mode);
                    plugin.getLogger().info("Scheduled random quest executed in " + mode + " mode");
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cq start " + questId + " " + mode);
                    plugin.getLogger().info("Scheduled quest " + questId + " executed in " + mode + " mode");
                }
            }
        };

        // reset if exists
        if (activeSchedules.containsKey(scheduleId)) {
            activeSchedules.get(scheduleId).cancel();
        }

        task.runTaskTimer(plugin, initialDelayTicks, intervalTicks);
        activeSchedules.put(scheduleId.toString(), task);
    }

    public void shutdown() {
        // Cancel all active schedules when the plugin is disabled
        for (BukkitRunnable task : activeSchedules.values()) {
            task.cancel();
        }
        activeSchedules.clear();
    }

    @Override
    public @NonNull String getPermission() {
        return "communityquests.schedule";
    }
}