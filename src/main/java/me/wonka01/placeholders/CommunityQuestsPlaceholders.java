package me.wonka01.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class CommunityQuestsPlaceholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "wonka01";
    }

    @Override
    public String getIdentifier() {
        return "communityquests";
    }

    @Override
    public String getVersion() {
        return "2.16";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {

        String questId = identifier.substring(identifier.lastIndexOf("_") + 1);

        if (identifier.startsWith("goal")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getQuestGoal());
            }
            return "0";
        }

        if (identifier.startsWith("complete")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getAmountCompleted());
            }
            return "0";
        }

        if (identifier.startsWith("time_remaining")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getQuestDuration());
            }
            return "0";
        }

        if (identifier.startsWith("name")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getDisplayName());
            }
            return "";
        }

        if (identifier.startsWith("description")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getDescription());
            }
            return "";
        }

        return null;
    }
}
