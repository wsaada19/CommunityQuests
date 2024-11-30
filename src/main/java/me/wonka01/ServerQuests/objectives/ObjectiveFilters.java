package me.wonka01.ServerQuests.objectives;

import me.knighthat.apis.utils.Utils;
import me.wonka01.ServerQuests.enums.ObjectiveType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Map;
import java.util.function.Predicate;

public class ObjectiveFilters {

    /**
     * Creates a filter for objectives based on various criteria
     */
    public static class Builder {
        private Material material;
        private String entity;
        private String customName;
        private Integer customModelId;
        private ObjectiveType type;
        private Map<Enchantment, Integer> enchantments;

        public Builder withMaterial(Material material) {
            this.material = material;
            return this;
        }

        public Builder withType(ObjectiveType type) {
            this.type = type;
            return this;
        }

        public Builder withItem(ItemStack item) {
            if (item != null) {
                this.material = item.getType();
                this.customModelId = Utils.getCustomModelData(item);
            }
            return this;
        }

        public Builder withPotion(PotionType potion) {
            if (potion != null) {
                this.customName = potion.name();
            }
            return this;
        }

        public Builder withEnchantments(Map<Enchantment, Integer> enchantments) {
            if (enchantments != null) {
                this.enchantments = enchantments;
            }
            return this;
        }

        public Builder withEntity(Entity entity) {
            if (entity != null) {
                this.entity = entity.getType().name();
                if (entity.getCustomName() != null) {
                    this.customName = entity.getCustomName();
                } else {
                    this.customName = entity.getName();
                }
            }
            return this;
        }

        // option to define with string for fishing/mythic mobs
        public Builder withEntity(String entity) {
            this.entity = entity;
            return this;
        }

        public Builder withCustomName(String customName) {
            this.customName = customName;
            return this;
        }

        public Builder withCustomModelId(Integer customModelId) {
            this.customModelId = customModelId;
            return this;
        }

        /**
         * Build a predicate to filter objectives
         * 
         * @param objective The objective to filter
         * @return true if the objective matches all specified criteria
         */
        public boolean matches(Objective objective) {
            // Material filter
            if (material != null) {
                if (!objective.getMaterials().contains(material) && !objective.getMaterials().isEmpty()) {
                    return false;
                }
            }

            if (type != null) {
                if (!objective.getType().equals(type)) {
                    return false;
                }
            }

            // Mob name filter
            if (entity != null) {
                if (!Utils.contains(objective.getMobNames(), entity) && !objective.getMobNames().isEmpty()) {
                    return false;
                }
            }

            // Custom name filter
            if (customName != null) {
                if (!Utils.contains(objective.getCustomNames(), customName) && !objective.getCustomNames().isEmpty()) {
                    return false;
                }
            }

            // Custom model ID filter
            if (customModelId != null) {
                if (!hasCustomModelId(objective, customModelId)) {
                    return false;
                }
            }

            // Enchantment filter
            if (enchantments != null) {
                // enchantments stored in customNames for now
                boolean containsOne = false;
                Bukkit.getLogger().info("Checking enchantments of size " + enchantments.size());
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    String enchantmentName = entry.getKey().getKey().toString();
                    Bukkit.getLogger().info("Checking enchantment named " + enchantmentName);
                    if (Utils.contains(objective.getCustomNames(), enchantmentName)) {
                        containsOne = true;
                        break;
                    }
                }

                if (!containsOne && !objective.getCustomNames().isEmpty()) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Check if an objective contains items with a specific custom model ID
         * 
         * @param objective The objective to check
         * @param modelId   The custom model ID to match
         * @return true if the objective contains an item with the specified custom
         *         model ID
         */
        private boolean hasCustomModelId(Objective objective, int modelId) {
            return objective.getCustomModelIds().contains(modelId);
        }
    }

    /**
     * Convenience method to create a new filter builder
     * 
     * @return A new ObjectiveFilters.Builder instance
     */
    public static Builder filter() {
        return new Builder();
    }

    /**
     * Additional utility method to check if an item matches a custom model ID
     * 
     * @param item          The ItemStack to check
     * @param customModelId The custom model ID to match
     * @return true if the item's custom model ID matches
     */
    public static boolean matchesCustomModelId(ItemStack item, int customModelId) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        return item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == customModelId;
    }

    /**
     * Create a predicate filter for objectives based on multiple criteria
     * 
     * @return A Predicate that can be used to filter objectives
     */
    public static Predicate<Objective> createObjectivePredicate(
            Material material,
            String entity,
            String customName,
            Integer customModelId) {
        return objective -> {
            // Null checks and filtering logic
            if (material != null
                    && (objective.getMaterials().isEmpty() || !objective.getMaterials().contains(material))) {
                return false;
            }

            if (entity != null
                    && (objective.getMobNames().isEmpty() || !Utils.contains(objective.getMobNames(), entity))) {
                return false;
            }

            if (customName != null && (objective.getCustomNames().isEmpty()
                    || !Utils.contains(objective.getCustomNames(), customName))) {
                return false;
            }

            // Custom model ID filter (placeholder - implement based on your Objective class
            // structure)
            if (customModelId != null) {
                // Implement custom model ID checking logic
                return false;
            }

            return true;
        };
    }
}