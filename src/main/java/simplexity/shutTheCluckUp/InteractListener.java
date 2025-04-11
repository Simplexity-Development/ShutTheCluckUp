package simplexity.shutTheCluckUp;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityInteract(PlayerInteractEntityEvent interactEvent){
        if (interactEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Player player = interactEvent.getPlayer();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();
        if (!itemUsed.getPersistentDataContainer().has(SilenceWand.silenceWandKey)) return;
        if (!(interactEvent.getRightClicked() instanceof LivingEntity livingEntity)) return;
        EntityType entityType = livingEntity.getType();
        if (!Util.playerHasEntityPerms(player, entityType)) return;
        Boolean silenced = SilenceLogic.toggleEntitySilence(livingEntity);
        if (silenced == null) {
            player.sendMessage("Sorry, that mob cannot be silenced as it was silenced by another plugin or command.");
            return;
        }
        if (silenced) {
            player.sendMessage(entityType + " is now silent!");
        } else {
            player.sendMessage(entityType + " is no longer silent!");
        }

    }
}
