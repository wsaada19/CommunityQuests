package me.knighthat.apis.files;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;
import me.wonka01.ServerQuests.questcomponents.QuestData;

public final class Messages extends Getters {

    public Messages(ServerQuests plugin) {
        super(plugin);
    }

    public @NonNull String getPrefix() {
        return color(string("prefix"));
    }

    public @NonNull String message(@NonNull String path) {
        return !get().contains(path) ? "" : color(getPrefix() + string(path));
    }

    public @NonNull String string(@NonNull String path, @NonNull QuestData quest) {

        String result = string(path).replace("questName", quest.getDisplayName());
        result = result.replace("questDescription", quest.getDescription());

        return result;
    }

    @Override
    public @NonNull String string(@NonNull String path) {
        return color(super.string(path));
    }
}
