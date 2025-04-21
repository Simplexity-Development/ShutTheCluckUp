package simplexity.shutthecluckup.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.ShutTheCluckUp;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;
import simplexity.shutthecluckup.configs.MessageUtils;
import simplexity.shutthecluckup.logic.Util;

import java.util.List;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class ShushWandCommand implements TabExecutor {

    public static final NamespacedKey silenceWandKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silence-wand");
    public static final NamespacedKey cooldownKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "wand-command-cooldown");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length < 1) return handleNoArgument(sender);
        if (!sender.hasPermission(Util.WAND_OTHER_COMMAND)) return false;
        String playerString = args[0];
        Player targetPlayer = Bukkit.getPlayer(playerString);
        if (targetPlayer == null) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_PLAYER.getMessage());
            return false;
        }
        if (!passedCooldown(sender, targetPlayer)) return false;
        giveWand(targetPlayer);
        sendSuccessMessage(sender, targetPlayer);
        return false;
    }

    private void sendSuccessMessage(@Nullable CommandSender sender, Player target) {
        boolean senderIsTarget = (sender instanceof Player && sender.equals(target));
        if (sender == null || senderIsTarget) {
            target.sendRichMessage(Message.FEEDBACK_WAND_RECEIVED_SUCCESS.getMessage());
            return;
        }
        sender.sendRichMessage(Message.FEEDBACK_WAND_GIVE_SUCCESS.getMessage(),
                Placeholder.parsed("name", target.getName()));
        target.sendRichMessage(Message.FEEDBACK_WAND_RECEIVED_SUCCESS.getMessage());
    }


    private boolean handleNoArgument(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_PLAYER.getMessage());
            return false;
        }
        if (!passedCooldown(sender, player)) return false;
        giveWand(player);
        sendSuccessMessage(sender, player);
        return true;
    }

    private boolean passedCooldown(CommandSender sender, Player targetPlayer) {
        long cooldownSecondsLeft = cooldownSecondsLeft(sender, targetPlayer);
        if (cooldownSecondsLeft != -1) {
            sender.sendRichMessage(Message.ERROR_COOLDOWN_NOT_EXPIRED.getMessage(),
                    MessageUtils.getTimeFormat(cooldownSecondsLeft));
            return false;
        }
        return true;
    }

    private void giveWand(Player player) {
        ItemStack wand = ConfigHandler.getInstance().getWandItemStack();
        wand.editPersistentDataContainer(pdc -> {
            pdc.set(silenceWandKey, PersistentDataType.BYTE, (byte) 1);
        });
        int emptySlot = player.getInventory().firstEmpty();
        if (emptySlot == -1) {
            Location playerLocation = player.getLocation().toCenterLocation();
            playerLocation.getWorld().dropItem(playerLocation, wand);
            return;
        }
        player.getInventory().setItem(emptySlot, wand);
    }

    private long cooldownSecondsLeft(CommandSender sender, Player player) {
        if (sender.hasPermission(Util.WAND_COOLDOWN_BYPASS)) {
            return -1;
        }
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        long currentTime = System.currentTimeMillis();
        long savedTime = playerPDC.getOrDefault(cooldownKey, PersistentDataType.LONG, -1L);
        if (savedTime == -1L) {
            playerPDC.set(cooldownKey, PersistentDataType.LONG, currentTime);
            return -1;
        }
        long cooldownTime = ConfigHandler.getInstance().getCooldownSeconds() * 1000L;
        long timeDiff = currentTime - savedTime;
        if (timeDiff > cooldownTime) {
            playerPDC.set(cooldownKey, PersistentDataType.LONG, currentTime);
            return -1;
        }
        return timeDiff / 1000L;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
