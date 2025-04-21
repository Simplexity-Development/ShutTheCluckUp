package simplexity.shutthecluckup.listeners;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import simplexity.shutthecluckup.logic.SilenceLogic;
import simplexity.shutthecluckup.commands.ShushWandCommand;
import simplexity.shutthecluckup.logic.Util;
import simplexity.shutthecluckup.configs.Message;

public class InteractListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityInteract(PlayerInteractEntityEvent interactEvent){
        if (interactEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Player player = interactEvent.getPlayer();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();
        if (!itemUsed.getPersistentDataContainer().has(ShushWandCommand.silenceWandKey))  return;
        if (!(interactEvent.getRightClicked() instanceof LivingEntity livingEntity)) return;
        EntityType entityType = livingEntity.getType();
        String entityTranslation = "<lang:" + entityType.translationKey() + ">";
        if (!Util.playerHasEntityPerms(player, entityType)) {
            player.sendRichMessage(Message.ERROR_NO_PERMISSION_FOR_THIS_ENTITY.getMessage(),
                    Placeholder.parsed("entity", entityTranslation));
            return;
        }
        Boolean silenced = SilenceLogic.toggleEntitySilence(livingEntity);
        if (silenced == null) {
            player.sendRichMessage(Message.MOB_CANNOT_BE_ALTERED.getMessage(),
                    Placeholder.parsed("entity", entityTranslation));
            return;
        }
        if (silenced) {
            player.sendRichMessage(Message.MOB_SILENCED.getMessage(),
                    Placeholder.parsed("entity", entityTranslation));
        } else {
            player.sendRichMessage(Message.MOB_UN_SILENCED.getMessage(),
                    Placeholder.parsed("entity", entityTranslation));
        }

    }
}
