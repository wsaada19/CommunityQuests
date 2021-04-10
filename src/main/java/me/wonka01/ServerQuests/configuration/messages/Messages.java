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
    private String questLimitReached;
    private String experience;
    private String competitive;
    private String cooperative;
    private String goal;
    private String you;
    private String leader;
    private String progress;
    private String cantDonate;
    private String viewMenu;
    private String startMenu;
    private String stopMenu;
    private String typeMenu;
    private String donateMenu;
    private String endQuestText;
    private String goBack;
    private String goBackText;
    private String clickToStart;

    public Messages(String noPermission, String invalidCommand, String reloadCommand, String helpMessage, String noActiveDonateQuests,
                    String invalidQuestName, String invalidQuestType, String noActiveQuests, String questComplete, String questStarted,
                    String contributionMessage, String topContributorsTitle, String rewardsTitle, String questLimitReached, String experience,
                    String competitive, String cooperative, String goal, String you, String leader, String progress, String cantDonate,
                    String viewMenu, String startMenu, String stopMenu, String typeMenu, String donateMenu, String endQuestText,
                    String goBack, String goBackText, String clickToStart) {
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
        this.questLimitReached = questLimitReached;
        this.experience = experience;
        this.competitive = competitive;
        this.cooperative = cooperative;
        this.goal = goal;
        this.you = you;
        this.leader = leader;
        this.progress = progress;
        this.cantDonate = cantDonate;
        this.viewMenu = viewMenu;
        this.startMenu= startMenu;
        this.stopMenu = stopMenu;
        this.typeMenu = typeMenu;
        this.donateMenu = donateMenu;
        this.endQuestText = endQuestText;
        this.goBack = goBack;
        this.goBackText = goBackText;
        this.clickToStart = clickToStart;
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

    public String getQuestLimitReached(){return questLimitReached;}

    public String getExperience() {return experience;}

    public String getCompetitive() {return competitive;}
    public String getCooperative() {return cooperative;}
    public String getGoal() {return goal;}
    public String getYou() {return you;}
    public String getLeader() {return leader;}
    public String getProgress() {return progress;}
    public String getCantDonate() {return cantDonate;}
    public String getViewMenu(){return viewMenu;}
    public String getStartMenu(){return startMenu;}
    public String getStopMenu(){return stopMenu;}
    public String getTypeMenu(){ return typeMenu; }
    public String getDonateMenu(){return donateMenu;}
    public String getEndQuestText(){return endQuestText;}
    public String getGoBack() {return goBack;}
    public String getGoBackText() {return goBackText;}
    public String getClickToStart() {return clickToStart;}

    private String convertQuestTokensToText(String message, QuestData data) {
        return message.replaceAll("questName", data.getDisplayName()).replaceAll("questDescription", data.getDescription());
    }
}
