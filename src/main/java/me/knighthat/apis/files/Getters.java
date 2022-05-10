package me.knighthat.apis.files;

import lombok.NonNull;
import me.wonka01.ServerQuests.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;

public abstract class Getters extends PluginFiles implements Colorization {

    protected Getters(ServerQuests plugin) {
        super(plugin);
    }

    public @NonNull String string(@NonNull String path) {
        return get().getString(path, "");
    }
}
