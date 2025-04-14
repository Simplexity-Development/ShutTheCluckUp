package simplexity.shutthecluckup;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.shutthecluckup.configs.ConfigHandler;

@SuppressWarnings("RedundantIfStatement")
public class Util {

    public static final Permission PASSIVE_PERM = new Permission("silence.mobs.passive");
    public static final Permission HOSTILE_PERM = new Permission("silence.mobs.hostile");
    public static final Permission OTHER_PERM = new Permission("silence.mobs.other");
    public static final Permission BYPASS_PERM = new Permission("silence.bypass.mob-type");
    public static final Permission WAND_COMMAND = new Permission("silence.wand");
    public static final Permission WAND_OTHER_COMMAND = new Permission("silence.other.wand");
    public static final Permission SILENCE_MOBS_COMMAND = new Permission("silence.command");
    public static final Permission SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE = new Permission("silence.bypass.radius");

    public static boolean playerHasEntityPerms(Player player, EntityType entityType) {
        if (player.hasPermission(BYPASS_PERM)) return true;
        if (ConfigHandler.getInstance().getEnabledPassives().contains(entityType)) {
            if (player.hasPermission(PASSIVE_PERM)) return true;
        }
        if (ConfigHandler.getInstance().getEnabledHostiles().contains(entityType)) {
            if (player.hasPermission(HOSTILE_PERM)) return true;
        }
        if (ConfigHandler.getInstance().getEnabledOther().contains(entityType)) {
            if (player.hasPermission(OTHER_PERM)) return true;
        }
        return false;
    }
}
