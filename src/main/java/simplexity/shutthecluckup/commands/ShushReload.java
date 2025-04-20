package simplexity.shutthecluckup.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;

public class ShushReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        ConfigHandler.getInstance().reloadConfigValues();
        commandSender.sendRichMessage(Message.FEEDBACK_CONFIG_RELOADED.getMessage());
        return false;
    }
}
