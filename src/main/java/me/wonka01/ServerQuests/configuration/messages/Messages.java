package me.wonka01.ServerQuests.configuration.messages;

public class Messages {
    private String noPermission;
    private String invalidCommand;
    private String reloadCommand;
    private String helpMessage;
    private String noActiveDonateQuests;
    private String invalidQuestName;
    private String invalidQuestType;
    private String noActiveQuests;


    public Messages(String noPermission, String invalidCommand, String reloadCommand, String helpMessage, String noActiveDonateQuests,
                    String invalidQuestName, String invalidQuestType, String noActiveQuests) {
        this.noPermission = noPermission;
        this.invalidCommand = invalidCommand;
        this.reloadCommand = reloadCommand;
        this.helpMessage = helpMessage;
        this.noActiveDonateQuests = noActiveDonateQuests;
        this.invalidQuestName = invalidQuestName;
        this.invalidQuestType = invalidQuestType;
        this.noActiveQuests = noActiveQuests;
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
}
