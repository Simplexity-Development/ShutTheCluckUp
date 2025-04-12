package simplexity.shutthecluckup.configs;

public enum Message {

    MOB_UN_SILENCED("silence-message.un-silenced","<green><entity> can be heard again!</green>"),
    MOB_SILENCED("silence-message.silenced", "<gray><entity> is now silent!</gray>"),
    MOB_CANNOT_BE_SILENCED("silence-message.cannot-be-silenced", "<red>Sorry, <entity> cannot be silenced as it has already been silenced by another plugin or command</red>"),
    FEEDBACK_WAND_SUCCESS("feedback.wand-success", "<green>Gave <name> a Silence Wand</green>"),
    ERROR_MUST_PROVIDE_PLAYER("error.must-provide-player", "<red>You must provide a player to send this wand to!</red>");

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
