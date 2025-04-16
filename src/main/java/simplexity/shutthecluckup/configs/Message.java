package simplexity.shutthecluckup.configs;

public enum Message {

    MOB_UN_SILENCED("silence-message.un-silenced","<green><entity> can be heard again!</green>"),
    MOB_SILENCED("silence-message.silenced", "<gray><entity> is now silent!</gray>"),
    MOB_CANNOT_BE_ALTERED("silence-message.cannot-be-altered", "<red>Sorry, <entity>'s 'silent' tag cannot be altered as it has already been silenced by another plugin or command</red>"),
    FEEDBACK_WAND_GIVE_SUCCESS("feedback.wand-give-success", "<green>Gave <name> a Silence Wand</green>"),
    FEEDBACK_WAND_RECEIVED_SUCCESS("feedback.wand-received-success", "<green>You have received a Silence Wand!</green>"),
    FEEDBACK_CONFIG_RELOADED("feedback.config-reloaded", "<green>Shut The Cluck Up Config Reloaded!</green>"),
    ERROR_NO_PERMISSION("error.no-permission", "<red>Sorry! You do not have permission to run this command!</red>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>Sorry, you must be a player to run this command!</red>"),
    ERROR_NO_PERMISSION_FOR_THIS_ENTITY("error.no-permission-for-this-entity", "<red>Sorry, you do not have permission to silence <entity></red>"),
    ERROR_MUST_PROVIDE_PLAYER("error.must-provide-player", "<red>You must provide a valid player to send this wand to!</red>"),
    ERROR_MUST_PROVIDE_ENTITY("error.must-provide-entity", "<red>You must provide a valid entity</red>"),
    ERROR_MUST_PROVIDE_RADIUS("error.must-provide-radius", "<red>You must provide a radius between 0 and <max></red>"),
    ERROR_RADIUS_TOO_LARGE("error.radius-too-large", "<red>The provided radius is too large. You must provide a radius between 0 and <max></red>");


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
