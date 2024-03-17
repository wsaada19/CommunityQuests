package me.wonka01.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.knighthat.apis.utils.Colorization;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
        String[] keywords = { "goal", "complete", "remaining", "name", "description", "you" };

        QuestController controller = null;
        QuestData questData = null;

        // If no Id is provided then return the first quest
        if (Utils.contains(keywords, questId) && quests.size() > 0) {
            controller = quests.get(0);
            questData = controller.getQuestData();
        }

        for (QuestController ctrl : quests) {
            if (ctrl.getQuestData().getQuestId().equals(questId)) {
                questData = ctrl.getQuestData();
                controller = ctrl;
            }
        }

        if (controller == null || questData == null) {
            return "no quest found";
        }

        // %communityquests_goal_<questId>%
        if (identifier.startsWith("goal")) {
            return String.valueOf(questData.getQuestGoal());
        }

        // %communityquests_complete_questId%
        if (identifier.startsWith("complete")) {
            return Utils.decimalToString(questData.getAmountCompleted());
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

        // %communityquests_you_questId%
        if (identifier.startsWith("you")) {
            if (player.isOnline()) {
                int playerContribution = (int) controller.getPlayerComponent().getAmountContributed((Player) player);
                return "" + playerContribution;
            }
            return "0";
        }
        return null;
    }
}
