package simplexity.shutTheCluckUp;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class SilenceLogic {

    public static final NamespacedKey silencedKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silenced");

    /**
     * Nullable method, toggles a living entity's silent state. If the state was set to silent and the entity does
     * not have the tag from this plugin, it will not be toggled and will return null
     * @param livingEntity entity to toggle
     * @return Boolean new silent state
     */
    @Nullable
    public static Boolean toggleEntitySilence(LivingEntity livingEntity) {
        PersistentDataContainer entityPDC = livingEntity.getPersistentDataContainer();
        if (livingEntity.isSilent() && !entityPDC.has(silencedKey)) {
            return null;
        }
        if (livingEntity.isSilent() && entityPDC.getOrDefault(silencedKey, PersistentDataType.BOOLEAN, false)) {
            livingEntity.setSilent(false);
            livingEntity.getPersistentDataContainer().remove(silencedKey);
            return false;
        }
        livingEntity.setSilent(true);
        livingEntity.getPersistentDataContainer().set(silencedKey, PersistentDataType.BOOLEAN, true);
        return true;
    }
}
