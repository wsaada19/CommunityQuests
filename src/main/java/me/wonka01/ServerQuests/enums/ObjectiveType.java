package me.wonka01.ServerQuests.enums;

import lombok.NonNull;

public enum ObjectiveType {
    MOB_Kill,
    CATCH_FISH,
    PLAYER_KILL,
    BLOCK_BREAK,
    PROJ_KILL,
    BLOCK_PLACE,
    SHEAR,
    TAME,
    GUI,
    MILK_COW,
    CRAFT_ITEM,
    CONSUME_ITEM,
    GIVE_MONEY,
    ENCHANT_ITEM,
    UNKNOWN;

    ObjectiveType() {
    }

    public static @NonNull ObjectiveType match(@NonNull String var) {

        for (ObjectiveType type : values())
            if (type.name().equalsIgnoreCase(var))
                return type;

        return UNKNOWN;
    }
}
