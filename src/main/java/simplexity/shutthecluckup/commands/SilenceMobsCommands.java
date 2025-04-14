package simplexity.shutthecluckup.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import org.bukkit.entity.Entity;
import simplexity.shutthecluckup.Util;

import java.util.List;

public class SilenceMobsCommands {

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("silence-mobs")
                .requires(sender -> sender.getSender().hasPermission(Util.SILENCE_MOBS_COMMAND))
                .then(Commands.argument("entity", ArgumentTypes.entity()))
                    .executes(context -> {
                        EntitySelectorArgumentResolver entitySelectorArgumentResolver = context.getArgument("entity")
                        List<Entity> gatheredEntities
                    })
    }
}
