package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@Getter
public class EventConstraints {
    private final List<Material> materials;
    private final List<String> mobNames;
    private final List<String> worlds;

    public EventConstraints(List<Material> materials, List<String> mobNames, List<String> worlds) {
        this.materials = materials;
        this.mobNames = mobNames;
        this.worlds = worlds;
    }
}
