package me.wonka01.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.knighthat.apis.utils.Colorization;
import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.ActiveQuests;
import me.wonka01.ServerQuests.questcomponents.QuestController;
import me.wonka01.ServerQuests.questcomponents.QuestData;
import me.wonka01.ServerQuests.questcomponents.schedulers.ParseDurationString;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] keywords = { "goal", "complete", "remaining", "name", "description", "you", "contribution",
                "progressbar", "you" };

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

        ServerQuests plugin = JavaPlugin.getPlugin(ServerQuests.class);
        String noQuestFound = plugin.messages().string("noQuestFound");
        if (controller == null || questData == null) {
            return noQuestFound;
        }

        // %communityquests_goal_<questId>%
        if (identifier.startsWith("goal")) {
            return String.valueOf(questData.getQuestGoal());
        }

        // %communityquests_complete_questId%
        if (identifier.startsWith("complete")) {
            return Utils.decimalToString(questData.getAmountCompleted());
        }

        // %communityquests_progressbar_questId%
        if (identifier.startsWith("progressbar")) {
            return questData.getProgressIndicator();
        }

        // %communityquests_time_remaining_questId%
        if (identifier.startsWith("time_remaining")) {
            return String.valueOf(ParseDurationString.formatSecondsToString(questData.getQuestDuration()));
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
        if (identifier.startsWith("rank_you")) {
            if (player.isOnline()) {
                int rank = controller.getPlayerComponent().getPlayerRank((Player) player);
                if (rank == 0) {
                    return plugin.messages().string("noPlayerAtRank");
                }
                return "" + rank;
            }
            return "0";
        }

        // %communityquests_you_questId%
        if (identifier.startsWith("you")) {
            if (player.isOnline()) {
                int playerContribution = (int) controller.getPlayerComponent().getAmountContributed((Player) player);
                return "" + playerContribution;
            }
            return "0";
        }

        // %communityquests_objective_goal_objId_questId%
        if (identifier.startsWith("objective_goal")) {
            int index = extractIndex(identifier.replace(questId, ""));
            return "" + questData.getObjectives().get(index).getGoal();
        }

        // %communityquests_objective_completed_objId_questId%
        if (identifier.startsWith("objective_completed")) {
            // get value of index from identifier and covert it to an integer
            int index = extractIndex(identifier.replace(questId, ""));
            return "" + questData.getObjectives().get(index).getAmountComplete();
        }

        // %communityquests_objective_objId_questId%
        if (identifier.startsWith("objective")) {
            int index = extractIndex(identifier.replace(questId, ""));
            return color(questData.getObjectives().get(index).getDescription());
        }

        // %communityquests_top_1_name_questId%
        // %communityquests_top_1_contribution_questId%

        if (identifier.startsWith("top")) {
            int index = extractIndex(identifier.replace(questId, "")) - 1;

            if (index < 0 || index >= controller.getPlayerComponent().getTopPlayers(index + 1).size()) {
                if (identifier.contains("name")) {
                    return plugin.messages().string("noPlayerAtRank");
                } else if (identifier.contains("contribution")) {
                    return plugin.messages().string("noPlayerContributionAtRank");
                }
                return null;
            }

            if (identifier.contains("name")) {
                return controller.getPlayerComponent().getTopPlayers(index + 1).get(index).getName();
            } else if (identifier.contains("contribution")) {
                return Utils.decimalToString(
                        controller.getPlayerComponent().getTopPlayers(index + 1).get(index).getAmountContributed());
            }
        }
        return null;
    }

    public static int extractIndex(String input) {
        Pattern pattern = Pattern.compile("\\d+"); // Match one or more digits
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0; // Return -1 if no integer is found in the string
    }
}
