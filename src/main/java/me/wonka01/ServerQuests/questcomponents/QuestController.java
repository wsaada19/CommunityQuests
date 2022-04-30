package me.wonka01.ServerQuests.questcomponents;

public class QuestController {

//    private final QuestBar questBar;
//    private final QuestData questData;
//    private final BasePlayerComponent playerComponent;
//    private final EventConstraints eventConstraints;
//    private final UUID questId;
//    private final ObjectiveType objective;
//
//    public QuestController(QuestData questData, QuestBar questBar,
//                           BasePlayerComponent playerComponent, EventConstraints eventConstraints,
//                           ObjectiveType objective) {
//        this.questData = questData;
//        this.questBar = questBar;
//        this.playerComponent = playerComponent;
//        questId = UUID.randomUUID();
//        this.eventConstraints = eventConstraints;
//        this.objective = objective;
//
//        if (questData.getQuestDuration() > 0) {
//            new QuestTimer(this);
//        }
//    }
//
//    public void updateQuest(int count, Player player) {
//        int amountToAdd = count;
//
//        if (questData.hasGoal()) {
//            if (count > questData.getQuestGoal() - questData.getAmountCompleted()) {
//                amountToAdd = questData.getQuestGoal() - questData.getAmountCompleted();
//            }
//            questData.addToQuestProgress(amountToAdd);
//        }
//
//        playerComponent.savePlayerAction(player, amountToAdd);
//        updateBossBar();
//        sendPlayerMessage(player);
//    }
//
//    public void endQuest() {
//        if (questData.hasGoal() && !questData.isGoalComplete() && questData.getQuestType().equalsIgnoreCase("coop")) {
//            broadcastQuestFailureMessage();
//            playerComponent.sendLeaderString();
//        } else {
//            broadcastVictoryMessage();
//            playerComponent.sendLeaderString();
//            playerComponent.giveOutRewards(questData.getQuestGoal());
//        }
//
//        questBar.removeBossBar();
//        ActiveQuests.getActiveQuestsInstance().endQuest(questId);
//    }
//
//    public void removeBossBar() {
//        questBar.removeBossBar();
//    }
//
//    public UUID getQuestId() {
//        return questId;
//    }
//
//    public QuestData getQuestData() {
//        return questData;
//    }
//
//    public BasePlayerComponent getPlayerComponent() {
//        return playerComponent;
//    }
//
//    public EventConstraints getEventConstraints() {
//        return eventConstraints;
//    }
//
//    public ObjectiveType getObjectiveType() {
//        return objective;
//    }
//
//    public boolean isCompetitive() {
//        return (questData instanceof CompetitiveQuestData);
//    }
//
//    public String getQuestType() {
//        return questData.getQuestType();
//    }
//
//    public QuestBar getQuestBar() {
//        return questBar;
//    }
//
//    private void updateBossBar() {
//        double barProgress = questData.getPercentageComplete();
//        questBar.updateBarProgress(barProgress);
//    }
//
//    private void sendPlayerMessage(Player player) {
//        if (player.hasPermission(PermissionNode.SHOW_MESSAGES)) {
//            String message = ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getContributionMessage(questData));
//            player.sendMessage(message);
//        }
//    }
//
//    public void broadcastStartMessage() {
//        String message = ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getQuestStarted(questData));
//        Bukkit.getServer().broadcastMessage(message);
//    }
//
//    private void broadcastVictoryMessage() {
//        String message = ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getQuestComplete(questData));
//        Bukkit.getServer().broadcastMessage(message);
//    }
//
//    private void broadcastQuestFailureMessage() {
//        String message = ChatColor.translateAlternateColorCodes('&', LanguageConfig.getConfig().getMessages().getQuestFailed(questData));
//        Bukkit.getServer().broadcastMessage(message);
//    }
}
