package simplexity.shutTheCluckUp;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

@SuppressWarnings("RedundantIfStatement")
public class Util {

    public static final Permission passivePerm = new Permission("silence.mobs.passive");
    public static final Permission hostilePerm = new Permission("silence.mobs.hostile");
    public static final Permission otherPerm = new Permission("silence.mobs.other");
    public static final Permission bypassPerm = new Permission("silence.bypass.mob-type");

    public static boolean playerHasEntityPerms(Player player, EntityType entityType) {
        if (player.hasPermission(bypassPerm)) return true;
        if (ConfigHandler.getInstance().getEnabledPassives().contains(entityType)) {
            if (player.hasPermission(passivePerm)) return true;
        }
        if (ConfigHandler.getInstance().getEnabledHostiles().contains(entityType)) {
            if (player.hasPermission(hostilePerm)) return true;
        }
        if (ConfigHandler.getInstance().getEnabledOther().contains(entityType)) {
            if (player.hasPermission(otherPerm)) return true;
        }
        return false;
    }
}
