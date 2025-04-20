package simplexity.shutthecluckup.commands;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public record CommandParameters(EntityType entity, double radius, boolean shouldSilence, Player player) {
}
