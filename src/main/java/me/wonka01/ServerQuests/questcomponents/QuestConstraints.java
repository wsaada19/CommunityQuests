package me.wonka01.ServerQuests.questcomponents;

import java.util.List;

public class QuestConstraints {

    private List<String> materialNames;
    private List<String> mobNames;

    public QuestConstraints(List<String> materialNames, List<String> mobNames) {
        this.materialNames = materialNames;
        this.mobNames = mobNames;
    }

    public List<String> getMaterialNames() {
        return materialNames;
    }

    public List<String> getMobNames() {
        return mobNames;
    }
}
