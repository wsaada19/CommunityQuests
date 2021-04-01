package me.wonka01.ServerQuests.configuration.messages;

import me.wonka01.ServerQuests.questcomponents.QuestData;

public class Messages {
    private String noPermission;
    private String invalidCommand;
    private String reloadCommand;
    private String helpMessage;
    private String noActiveDonateQuests;
    private String invalidQuestName;
    private String invalidQuestType;
    private String noActiveQuests;
    private String questComplete;
    private String questStarted;
    private String contributionMessage;
    private String topContributorsTitle;
    private String rewardsTitle;


    public Messages(String noPermission, String invalidCommand, String reloadCommand, String helpMessage, String noActiveDonateQuests,
                    String invalidQuestName, String invalidQuestType, String noActiveQuests, String questComplete, String questStarted,
                    String contributionMessage, String topContributorsTitle, String rewardsTitle) {
        this.noPermission = noPermission;
        this.invalidCommand = invalidCommand;
        this.reloadCommand = reloadCommand;
        this.helpMessage = helpMessage;
        this.noActiveDonateQuests = noActiveDonateQuests;
        this.invalidQuestName = invalidQuestName;
        this.invalidQuestType = invalidQuestType;
        this.noActiveQuests = noActiveQuests;
        this.questComplete = questComplete;
        this.questStarted = questStarted;
        this.contributionMessage = contributionMessage;
        this.topContributorsTitle = topContributorsTitle;
        this.rewardsTitle = rewardsTitle;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getInvalidCommand() {
        return invalidCommand;
    }

    public String getReloadCommand() {
        return reloadCommand;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public String getNoActiveDonateQuests() {
        return noActiveDonateQuests;
    }

    public String getInvalidQuestName() {
        return invalidQuestName;
    }

    public String getInvalidQuestType() {
        return invalidQuestType;
    }

    public String getNoActiveQuests() {
        return noActiveQuests;
    }

    public String getTopContributorsTitle(){
        return topContributorsTitle;
    }

    public String getQuestComplete(QuestData questData) {
        return convertQuestTokensToText(questComplete, questData);
    }

    public String getQuestStarted(QuestData questData) {
        return convertQuestTokensToText(questStarted, questData);
    }

    public String getContributionMessage(QuestData questData) {
        return convertQuestTokensToText(contributionMessage, questData);
    }

    public String getRewardsTitle() {
        return rewardsTitle;
    }

    private String convertQuestTokensToText(String message, QuestData data) {
        message.replaceAll("questName", data.getDisplayName());
        message.replaceAll("questDescription", data.getDescription());
        return message;
    }
}
