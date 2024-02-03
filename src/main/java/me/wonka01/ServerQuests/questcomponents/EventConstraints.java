package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;

import java.util.List;

@Getter
public class EventConstraints {
    private List<String> materialNames;
    private List<String> mobNames;
    private List<String> worlds;

    public EventConstraints(List<String> materialNames, List<String> mobNames, List<String> worlds) {
        this.materialNames = materialNames;
        this.mobNames = mobNames;
        this.worlds = worlds;
    }
}
