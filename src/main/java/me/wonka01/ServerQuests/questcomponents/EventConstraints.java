package me.wonka01.ServerQuests.questcomponents;

import lombok.Getter;

import java.util.List;

@Getter
public class EventConstraints {
    private final List<String> worlds;

    public EventConstraints(List<String> worlds) {
        this.worlds = worlds;
    }
}
