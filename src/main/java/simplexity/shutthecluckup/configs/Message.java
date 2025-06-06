package simplexity.shutthecluckup.configs;

public enum Message {

    MOB_UN_SILENCED("shush-single.un-silenced","<green><entity> can be heard again!</green>"),
    MOB_SILENCED("shush-single.silenced", "<gray><entity> is now silent!</gray>"),
    MOB_CANNOT_BE_ALTERED("shush-single.cannot-be-altered", "<red>Sorry, <entity>'s 'silent' tag cannot be altered as it has already been silenced by another plugin or command</red>"),
    MOB_GROUP_SILENCED("shush-group.silenced", "<count> <entity>(s) were silenced"),
    MOB_GROUP_UNSILENCED("shush-group.unsilenced", "<count> <entity>(s) are now un-silenced"),
    MOB_GROUP_CANNOT_BE_ALTERED("shush-group.cannot-be-altered", "<count> <entity>(s) could not be altered as they have already been silenced by another plugin or command"),
    FEEDBACK_WAND_GIVE_SUCCESS("plugin-feedback.wand-give-success", "<green>Gave <name> a Silence Wand</green>"),
    FEEDBACK_WAND_RECEIVED_SUCCESS("plugin-feedback.wand-received-success", "<green>You have received a Silence Wand!</green>"),
    FEEDBACK_CONFIG_RELOADED("plugin-feedback.config-reloaded", "<green>Shut The Cluck Up Config Reloaded!</green>"),
    INSERT_TIME_FORMAT_GROUP("insert.time-format.group", "<hour><min><sec>"),
    INSERT_TIME_FORMAT_HOUR("insert.time-format.hour", " <yellow><count></yellow> hour"),
    INSERT_TIME_FORMAT_HOURS("insert.time-format.hours", " <yellow><count></yellow> hours"),
    INSERT_TIME_FORMAT_MINUTE("insert.time-format.minute", " <yellow><count></yellow> min"),
    INSERT_TIME_FORMAT_MINUTES("insert.time-format.minute", " <yellow><count></yellow> mins"),
    INSERT_TIME_FORMAT_SECOND("insert.time-format.second", " <yellow><count></yellow> sec"),
    INSERT_TIME_FORMAT_SECONDS("insert.time-format.second", " <yellow><count></yellow> secs"),
    ERROR_SUBCOMMAND_NOT_RECOGNIZED("error.subcommand-not-recognized", "<red>Sorry, '<input>' is not a recognized or valid subcommand, please check your syntax and try again"),
    ERROR_NO_PERMISSION("error.no-permission", "<red>Sorry! You do not have permission to run this command!</red>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>Sorry, you must be a player to run this command!</red>"),
    ERROR_NO_PERMISSION_FOR_THIS_ENTITY("error.no-permission-for-this-entity", "<red>Sorry, you do not have permission to silence <entity></red>"),
    ERROR_MUST_PROVIDE_PLAYER("error.must-provide-player", "<red>You must provide a valid player to send this wand to!</red>"),
    ERROR_NOT_ENOUGH_ARGUMENTS("error.not-enough-arguments", "<red>Not enough arguments provided. You must provide an entity and a radius."),
    ERROR_INVALID_ENTITY("error.invalid-entity", "<red>You must provide a valid entity</red>"),
    ERROR_NON_LIVING_ENTITY_PROVIDED("error.non-living-entity-provided", "<red>You have provided a non-living entity, you must provide a valid living entity"),
    ERROR_INVALID_RADIUS("error.invalid-radius", "<red>You must provide a radius between 0 and <max></red>"),
    ERROR_INVALID_RADIUS_NUMBER("error.invalid-radius-number", "<red>You must provide a radius that is above 0 and is a number"),
    ERROR_NO_MOBS_FOUND("error.no-mobs-found", "<red>Could not find any <entity>s in a range of <radius> blocks</red>"),
    ERROR_COOLDOWN_NOT_EXPIRED("error.cooldown-not-expired", "<red>Sorry! That command is on cooldown for<time> longer</red>"),
    ERROR_NO_VIABLE_MOBS_FOUND("error.no-viable-mobs-found", "<red>Unable to find any <entity>s within <radius> blocks that do not already have the 'silent' tag set to <state>");


    private final String path;
    private String message;

    Message(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
