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
    public String onRequest(OfflinePlayer player, String identifier) {

        if (identifier.equals("goal")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getQuestGoal());
            }
            return "0";
        }

        if (identifier.equals("complete")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getAmountCompleted());
            }
            return "0";
        }

        if (identifier.equals("time_remaining")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getQuestDuration());
            }
            return "0";
        }

        if (identifier.equals("name")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getDisplayName());
            }
            return "";
        }

        if (identifier.equals("description")) {
            List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
            if(quests.size() > 0) {
                return String.valueOf(quests.get(0).getQuestData().getDescription());
            }
            return "";
        }

        return null;
    }
}
