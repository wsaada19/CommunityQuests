package me.knighthat.apis.files;

import lombok.NonNull;
import me.wonka01.ServerQuests.ServerQuests;

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
}
