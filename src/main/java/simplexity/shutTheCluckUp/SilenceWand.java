package simplexity.shutTheCluckUp;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class SilenceWand implements CommandExecutor {

    public static final NamespacedKey silenceWandKey = new NamespacedKey(ShutTheCluckUp.getInstance(), "silence-wand");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only a player can execute this command");
            return false;
        }
        ItemStack wand = ConfigHandler.getInstance().getWandItemStack();
        wand.editPersistentDataContainer(pdc -> {
            pdc.set(silenceWandKey, PersistentDataType.BYTE, (byte) 1);
        });
        int emptySlot = player.getInventory().firstEmpty();
        if (emptySlot == -1) {
            Location playerLocation = player.getLocation().toCenterLocation();
            playerLocation.getWorld().dropItem(playerLocation, wand);
            return true;
        }
        player.getInventory().setItem(emptySlot, wand);
        return false;
    }
}
