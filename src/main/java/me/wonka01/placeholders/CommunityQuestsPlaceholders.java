package me.wonka01.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.utils.Colorization;
import me.wonka01.ServerQuests.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class CommunityQuestsPlaceholders extends PlaceholderExpansion implements Colorization {

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
        return "1.3.4";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        String questId = identifier.substring(identifier.lastIndexOf("_") + 1);
        List<QuestController> quests = ActiveQuests.getActiveQuestsInstance().getActiveQuestsList();
        QuestData questData = null;

        for (QuestController controller : quests) {
            if (controller.getQuestData().getQuestId().equals(questId)) {
                questData = controller.getQuestData();
            }
        }

        if (questData == null) {
            return "no quest found";
        }

        // %communityquests_goal_<questId>%
        if (identifier.startsWith("goal")) {
            return String.valueOf(questData.getQuestGoal());
        }

        // %communityquests_complete_questId%
        if (identifier.startsWith("complete")) {
            return NumberUtils.decimals(questData.getAmountCompleted());
        }

        // %communityquests_time_remaining_questId%
        if (identifier.startsWith("time_remaining")) {
            return String.valueOf(questData.getQuestDuration());
        }

        // %communityquests_name_questId%
        if (identifier.startsWith("name")) {
            return color(questData.getDisplayName());
        }

        // %communityquests_description_questId%
        if (identifier.startsWith("description")) {
            return color(questData.getDescription());
        }
        return null;
    }
}
