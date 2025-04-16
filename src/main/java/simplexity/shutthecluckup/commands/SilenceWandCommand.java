package simplexity.shutthecluckup.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.shutthecluckup.ShutTheCluckUp;
import simplexity.shutthecluckup.Util;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;

import java.util.List;

public class SilenceWandCommand implements TabExecutor {

    public static final NamespacedKey silenceWandKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silence-wand");

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


    private boolean handleNoArgument(CommandSender sender){
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_PLAYER.getMessage());
            return false;
        } else {
            giveWand(player);
            sendSuccessMessage(sender, player);
            return true;
        }
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



    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
