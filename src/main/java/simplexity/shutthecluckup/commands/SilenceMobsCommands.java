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
import simplexity.shutthecluckup.logic.SilenceLogic;
import simplexity.shutthecluckup.logic.Util;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;

import java.util.Collection;
import java.util.List;

public class SilenceMobsCommands implements TabExecutor {

    private final MiniMessage miniMessage = ShutTheCluckUp.getMiniMessage();

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
        boolean shouldSilence;
        if (args.length < 3) {
            shouldSilence = true;
        } else {
            shouldSilence = Boolean.parseBoolean(args[2]);
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
        Double radiusDouble = Util.validateDouble(args[1]);
        if (radiusDouble == null || ((radiusDouble > ConfigHandler.getInstance().getMaxRadius() && radiusDouble < 0.0) &&
                                     !playerSender.hasPermission(Util.SILENCE_MOBS_COMMAND_RADIUS_OVERRIDE))) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_RADIUS.getMessage(),
                    Placeholder.parsed("max", String.valueOf(ConfigHandler.getInstance().getMaxRadius())));
            return false;
        }
        Location playerLocation = playerSender.getLocation();
        Collection<LivingEntity> nearbyEntities = playerLocation.getNearbyLivingEntities(radiusDouble, entity -> entity.getType().equals(providedEntity));
        if (nearbyEntities.isEmpty()) {
            playerSender.sendMessage(miniMessage.deserialize(Message.ERROR_NO_MOBS_FOUND.getMessage(),
                    Placeholder.parsed("entity", "<lang:" + providedEntity.translationKey() + ">"),
                    Placeholder.parsed("radius", String.valueOf(radiusDouble))));
        }
        int successful = 0;
        int unableToBeModified = 0;
        for (LivingEntity livingEntity : nearbyEntities) {
            Boolean silencingSuccessful = SilenceLogic.setSilentTag(livingEntity, shouldSilence);
            if (silencingSuccessful == null) {
                unableToBeModified ++;
                continue;
            }
            if (silencingSuccessful) {
                successful ++;
            }
        }
        if (successful == 0 && unableToBeModified == 0) {
            playerSender.sendMessage(miniMessage.deserialize(Message.ERROR_NO_VIABLE_MOBS_FOUND.getMessage(),
                    Placeholder.parsed("entity", "<lang:" + providedEntity.translationKey() + ">"),
                    Placeholder.parsed("radius", String.valueOf(radiusDouble)),
                    Placeholder.parsed("state", String.valueOf(shouldSilence))));
            return false;
        }
        Component messageToSend = Component.empty();

        if (shouldSilence) {
            messageToSend = messageToSend.append(parseMessage(successful, providedEntity, Message.MOB_GROUP_SILENCED.getMessage()));
        } else {
            messageToSend = messageToSend.append(parseMessage(successful, providedEntity, Message.MOB_GROUP_UNSILENCED.getMessage()));
        }
        Component unModifiable = parseMessage(unableToBeModified, providedEntity, Message.MOB_GROUP_CANNOT_BE_ALTERED.getMessage());
        if (!unModifiable.equals(Component.empty())) {
            messageToSend = messageToSend.appendNewline();
            messageToSend = messageToSend.append(unModifiable);
        }
        playerSender.sendMessage(messageToSend);
        return true;
    }

    private Component parseMessage(int numberModified, EntityType entity, String message) {
        if (numberModified == 0) return Component.empty();
        String entityName = "<lang:" + entity.translationKey() + ">";
        return miniMessage.deserialize(message,
                Placeholder.parsed("count", String.valueOf(numberModified)),
                Placeholder.parsed("entity", entityName));
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        return List.of();
    }
}
