package simplexity.shutthecluckup.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import simplexity.shutthecluckup.ShutTheCluckUp;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.configs.Message;

@SuppressWarnings("UnstableApiUsage")
public class SilenceWandCommand {

    public static final NamespacedKey silenceWandKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silence-wand");

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("silence-wand")
                .executes(context -> {
                    CommandSourceStack sourceStack = context.getSource();
                    CommandSender sender = sourceStack.getSender();
                    if (!(sender instanceof Player player)) {
                        sender.sendRichMessage(Message.ERROR_MUST_PROVIDE_PLAYER.getMessage());
                        return 0;
                    } else {
                        giveWand(player);
                        sender.sendRichMessage(Message.FEEDBACK_WAND_SUCCESS.getMessage(),
                                Placeholder.parsed("name", sender.getName()));
                        return Command.SINGLE_SUCCESS;
                    }
                })
                .then(
                        Commands.argument("target", ArgumentTypes.player())
                                .executes(
                                        context -> {
                                            CommandSender sender = context.getSource().getSender();
                                            PlayerSelectorArgumentResolver targetResolver = context.getArgument("target", PlayerSelectorArgumentResolver.class);
                                            Player target = targetResolver.resolve(context.getSource()).getFirst();
                                            giveWand(target);
                                            sender.sendRichMessage(Message.FEEDBACK_WAND_SUCCESS.getMessage(),
                                                    Placeholder.parsed("name", target.getName()));
                                            return Command.SINGLE_SUCCESS;
                                        }
                                )).build();
    }

    private static void giveWand(Player player) {
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

}
