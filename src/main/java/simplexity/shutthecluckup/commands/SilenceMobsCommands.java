package simplexity.shutthecluckup.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.Util;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;

import java.util.List;

public class SilenceMobsCommands implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage(Message.ERROR_MUST_BE_PLAYER.getMessage());
            return false;
        }
        if (args.length < 1) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_ENTITY.getMessage());
            return false;
        }
        if (args.length < 2) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_RADIUS.getMessage(),
                    Placeholder.parsed("max", String.valueOf(ConfigHandler.getInstance().getMaxRadius())));
            return false;
        }
        EntityType providedEntity = Util.validateEntityType(args[0]);
        if (providedEntity == null) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_ENTITY.getMessage());
            return false;
        }
        if (!Util.playerHasEntityPerms(playerSender, providedEntity)) {
            sender.sendRichMessage(Message.ERROR_NO_PERMISSION_FOR_THIS_ENTITY.getMessage(),
                    Placeholder.parsed("entity", providedEntity.name()));
            return false;
        }
        Integer radiusInt = Util.validateInteger(args[1]);
        if (radiusInt == null || (radiusInt < ConfigHandler.getInstance().getMaxRadius() && radiusInt > 0)) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_RADIUS.getMessage(),
                    Placeholder.parsed("max", String.valueOf(ConfigHandler.getInstance().getMaxRadius())));
            return false;
        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
