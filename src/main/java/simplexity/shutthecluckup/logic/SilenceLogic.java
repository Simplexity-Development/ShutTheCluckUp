package simplexity.shutthecluckup.logic;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.shutthecluckup.ShutTheCluckUp;

import javax.annotation.Nullable;

public class SilenceLogic {

    public static final NamespacedKey silencedKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silenced");

    /**
     * Nullable method, toggles a living entity's silent state. If the state was set to silent and the entity does
     * not have the tag from this plugin, it will not be toggled and will return null
     *
     * @param livingEntity entity to toggle
     * @return Boolean new silent state
     */
    @Nullable
    public static Boolean toggleEntitySilence(LivingEntity livingEntity) {
        Boolean currentlySilenced = entityIsSilencedByPlugin(livingEntity);
        if (currentlySilenced == null) return null;
        if (currentlySilenced) {
            setSilentTag(livingEntity, false);
            return false;
        }
        setSilentTag(livingEntity, true);
        return true;
    }


    /**
     * Set an entity's silent tag state.
     *
     * @param livingEntity LivingEntity to silence, or unsilence
     * @param silence      boolean state to set silence tag to
     * @return boolean -
     * true if successfully changed
     * false if already set
     * null if cannot be set
     */
    public static Boolean setSilentTag(LivingEntity livingEntity, boolean silence) {
        Boolean currentlySilenced = entityIsSilencedByPlugin(livingEntity);
        if (currentlySilenced == null) return null;
        PersistentDataContainer entityPdc = livingEntity.getPersistentDataContainer();
        if ((currentlySilenced && silence) || (!currentlySilenced && !silence)) return false;
        if (silence) {
            livingEntity.setSilent(true);
            entityPdc.set(silencedKey, PersistentDataType.BOOLEAN, true);
            return true;
        }
        livingEntity.setSilent(false);
        entityPdc.remove(silencedKey);
        return true;
    }


    /**
     * Checks if this entity is currently silenced by this plugin.
     *
     * @param livingEntity entity to check
     * @return Boolean
     * true: The entity is currently silenced by this plugin
     * false: The entity is not currently silenced by this plugin
     * null: The entity is currently silenced by another plugin or command
     */
    @Nullable
    public static Boolean entityIsSilencedByPlugin(LivingEntity livingEntity) {
        PersistentDataContainer entityPdc = livingEntity.getPersistentDataContainer();
        if (!livingEntity.isSilent()) return false;
        if (!entityPdc.has(silencedKey)) return null;
        return true;
    }

}
