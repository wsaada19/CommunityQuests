package me.wonka01.ServerQuests.enums;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;

public enum ObjectiveType {
    MOB_Kill("mobkill", Material.DIAMOND_SWORD),
    CATCH_FISH("catchfish", Material.FISHING_ROD),
    PLAYER_KILL("playerkill", Material.CHAINMAIL_CHESTPLATE),
    BLOCK_BREAK("blockbreak", Material.DIAMOND_PICKAXE),
    PROJ_KILL("projectilekill", Material.BOW),
    BLOCK_PLACE("blockplace", Material.DIRT),
    SHEAR("shear", Material.SHEARS),
    TAME("team", Material.BONE),
    GUI("donate", Material.MILK_BUCKET),
    MILK_COW("milkcow", Material.CHEST),
    CRAFT_ITEM("craftitem", Material.CHAINMAIL_HELMET),
    CONSUME_ITEM("consumeitem", Material.APPLE),
    GIVE_MONEY("money", Material.GOLDEN_APPLE),
    ENCHANT_ITEM("enchantitem", Material.EMERALD),
    UNKNOWN("unknown", Material.AIR);

    private final @NonNull String string;

    @Getter
    private final @NonNull Material defaultMaterial;

    ObjectiveType(@NonNull String string, @NonNull Material defaultMaterial) {
        this.string = string;
        this.defaultMaterial = defaultMaterial;
    }

    public static @NonNull ObjectiveType match(@NonNull String var) {

        for (ObjectiveType type : values())
            if (type.name().equalsIgnoreCase(var) ||
                type.string.equalsIgnoreCase(var))
                return type;

        return UNKNOWN;
    }
}
