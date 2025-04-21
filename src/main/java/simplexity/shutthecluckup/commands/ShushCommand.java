package simplexity.shutthecluckup.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.ShutTheCluckUp;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.logic.SilenceLogic;
import simplexity.shutthecluckup.logic.Util;
import simplexity.shutthecluckup.configs.Message;

import java.util.Collection;
import java.util.List;

public class ShushCommand implements TabExecutor {

    private final MiniMessage miniMessage = ShutTheCluckUp.getMiniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        CommandParameters parameters = getCommandParameters(sender, args);
        if (parameters == null) return false;
        handleSilenceMobs(parameters);
        return true;
    }

    @Nullable
    private CommandParameters getCommandParameters(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_BE_PLAYER.getMessage());
            return null;
        }
        if (args.length < 2) {
            player.sendRichMessage(Message.ERROR_NOT_ENOUGH_ARGUMENTS.getMessage());
            return null;
        }
        EntityType entity = Util.validateEntityType(args[0]);
        if (entity == null) {
            player.sendRichMessage(Message.ERROR_INVALID_ENTITY.getMessage());
            return null;
        }
        if (!entity.isAlive()) {
            player.sendRichMessage(Message.ERROR_NON_LIVING_ENTITY_PROVIDED.getMessage());
            return null;
        }
        if (!Util.playerHasEntityPerms(player, entity)) {
            player.sendRichMessage(Message.ERROR_NO_PERMISSION_FOR_THIS_ENTITY.getMessage());
            return null;
        }
        Double radius = Util.validateDouble(args[1]);
        double maxRadius = ConfigHandler.getInstance().getMaxRadius();
        if (radius == null) {
            if (player.hasPermission(Util.SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE)) {
                player.sendRichMessage(Message.ERROR_INVALID_RADIUS_NUMBER.getMessage());
            } else {
                player.sendRichMessage(Message.ERROR_INVALID_RADIUS.getMessage(),
                        Placeholder.parsed("max", String.valueOf(maxRadius)));
            }
            return null;
        }
        if ((radius < 0 || radius > maxRadius) && !player.hasPermission(Util.SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE)) {
            player.sendRichMessage(Message.ERROR_INVALID_RADIUS.getMessage(),
                    Placeholder.parsed("max", String.valueOf(maxRadius)));
            return null;
        }
        boolean shouldSilence;
        if (args.length >= 3) {
            Boolean requestedState = Util.validateBoolean(args[2]);
            if (requestedState == null) {
                player.sendRichMessage(Message.ERROR_SUBCOMMAND_NOT_RECOGNIZED.getMessage(),
                        Placeholder.parsed("input", args[2]));
                return null;
            }
            shouldSilence = requestedState;
        } else {
            shouldSilence = true;
        }
        return new CommandParameters(entity, radius, shouldSilence, player);
    }

    private void handleSilenceMobs(CommandParameters parameters) {
        Player player = parameters.player();
        Location location = player.getLocation();
        double radius = parameters.radius();
        boolean shouldSilence = parameters.shouldSilence();
        EntityType entityType = parameters.entity();
        Collection<LivingEntity> nearbyEntities = location.getNearbyLivingEntities(parameters.radius(), entity -> entity.getType().equals(entityType));
        if (nearbyEntities.isEmpty()) {
            player.sendRichMessage(Message.ERROR_NO_MOBS_FOUND.getMessage(),
                    Placeholder.parsed("entity", "<lang:" + entityType.translationKey() + ">"),
                    Placeholder.parsed("radius", String.valueOf(radius)));
            return;
        }
        int successful = 0;
        int unableToBeModified = 0;
        for (LivingEntity livingEntity : nearbyEntities) {
            Boolean silencingSuccessful = SilenceLogic.setSilentTag(livingEntity, shouldSilence);
            if (silencingSuccessful == null) {
                unableToBeModified++;
                continue;
            }
            if (silencingSuccessful) {
                successful++;
            }
        }
        if (successful == 0 && unableToBeModified == 0) {
            player.sendMessage(parseEntityMessage(0, entityType, Message.ERROR_NO_VIABLE_MOBS_FOUND.getMessage(),
                    radius, shouldSilence));
            return;
        }
        String message;
        if (shouldSilence) {
            message = Message.MOB_GROUP_SILENCED.getMessage();
        } else {
            message = Message.MOB_UN_SILENCED.getMessage();
        }
        Component componentToSend = parseEntityMessage(successful, entityType, message, radius, shouldSilence);
        if (unableToBeModified > 0) {
            componentToSend = componentToSend.appendNewline();
            componentToSend = componentToSend.append(parseEntityMessage(unableToBeModified, entityType, Message.MOB_GROUP_CANNOT_BE_ALTERED.getMessage(), radius, shouldSilence));
        }
        player.sendMessage(componentToSend);
    }


    private Component parseEntityMessage(int numberModified, EntityType entity, String message, Double radius, Boolean state) {
        String entityName = "<lang:" + entity.translationKey() + ">";
        return miniMessage.deserialize(message,
                Placeholder.parsed("count", String.valueOf(numberModified)),
                Placeholder.parsed("entity", entityName),
                Placeholder.parsed("radius", String.valueOf(radius)),
                Placeholder.parsed("state", String.valueOf(state).toLowerCase()));
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) return null;
        if (!sender.hasPermission(Util.SILENCE_MOBS_COMMAND)) return List.of();
        if (args.length < 2) {
            if (sender.hasPermission(Util.MOB_TYPE_BYPASS))
                return ConfigHandler.getInstance().getAllLivingEntityNames();
            if (sender.hasPermission(Util.BASIC_PERM)) return ConfigHandler.getInstance().getEnabledMobNames();
        }
        if (args.length == 2) {
            return List.of("radius");
        }
        if (args.length == 3) {
            return List.of("true", "false");
        }
        return List.of();
    }
}
