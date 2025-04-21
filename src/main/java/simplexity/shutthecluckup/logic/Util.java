package simplexity.shutthecluckup.logic;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.configs.ConfigHandler;

@SuppressWarnings({"RedundantIfStatement", "BooleanMethodIsAlwaysInverted"})
public class Util {

    public static final Permission BASIC_PERM = new Permission("shush.mob-type.basic");
    public static final Permission MOB_TYPE_BYPASS = new Permission("shush.bypass.mob-type");
    public static final Permission WAND_OTHER_COMMAND = new Permission("shush.other.wand");
    public static final Permission WAND_COOLDOWN_BYPASS = new Permission("shush.bypass.cooldown");
    public static final Permission SILENCE_MOBS_COMMAND = new Permission("shush.command");
    public static final Permission SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE = new Permission("shush.bypass.radius");

    public static boolean playerHasEntityPerms(Player player, EntityType entityType) {
        if (player.hasPermission(MOB_TYPE_BYPASS)) return true;
        if (ConfigHandler.getInstance().getEnabledMobs().contains(entityType)) {
            if (player.hasPermission(BASIC_PERM)) return true;
        }
        if (player.hasPermission("shush.mob-type." + entityType.toString().toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * Takes in a string and returns an Entity Type, or null if unable to find an entity type
     * @param entityString String to check
     * @return EntityType entity found
     */
    @Nullable
    public static EntityType validateEntityType(String entityString) {
        if (entityString == null || entityString.isEmpty()) return null;
        try {
            return EntityType.valueOf(entityString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Takes in a string and returns a double, or null if the string cannot be turned into a double
     * @param doubleString String to check
     * @return Double the double found
     */
    @Nullable
    public static Double validateDouble(String doubleString) {
        if (doubleString == null || doubleString.isEmpty()) return null;
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Takes in a string and returns a boolean, or null if the string cannot be turned into a boolean
     * @param booleanString String to check
     * @return Boolean the boolean found
     */
    @Nullable
    public static Boolean validateBoolean(String booleanString) {
        if (booleanString == null || booleanString.isEmpty()) return null;
        booleanString = booleanString.trim();
        if (booleanString.equalsIgnoreCase("true")) return true;
        if (booleanString.equalsIgnoreCase("false")) return false;
        return null;
    }
}
