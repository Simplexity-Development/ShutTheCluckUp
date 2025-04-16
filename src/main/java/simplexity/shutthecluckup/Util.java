package simplexity.shutthecluckup;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.configs.ConfigHandler;

@SuppressWarnings("RedundantIfStatement")
public class Util {

    public static final Permission BASIC_PERM = new Permission("silence.mobs.passive");
    public static final Permission MOB_TYPE_BYPASS = new Permission("silence.bypass.mob-type");
    public static final Permission WAND_OTHER_COMMAND = new Permission("silence.other.wand");
    public static final Permission SILENCE_MOBS_COMMAND = new Permission("silence.command");
    public static final Permission SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE = new Permission("silence.bypass.radius");

    public static boolean playerHasEntityPerms(Player player, EntityType entityType) {
        if (player.hasPermission(MOB_TYPE_BYPASS)) return true;
        if (ConfigHandler.getInstance().getEnabledMobs().contains(entityType)) {
            if (player.hasPermission(BASIC_PERM)) return true;
        }
        if (player.hasPermission("silence.mobs." + entityType.toString().toLowerCase())) {
            return true;
        }
        return false;
    }

    @Nullable
    public static EntityType validateEntityType(String entityString){
        try {
            return EntityType.valueOf(entityString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    public static Integer validateInteger(String intString){
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
