package simplexity.shutthecluckup;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.shutthecluckup.commands.SilenceMobsCommands;
import simplexity.shutthecluckup.commands.SilenceReload;
import simplexity.shutthecluckup.commands.SilenceWandCommand;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.listeners.InteractListener;

@SuppressWarnings("UnstableApiUsage")
public final class ShutTheCluckUp extends JavaPlugin {

    private static ShutTheCluckUp instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        ConfigHandler.getInstance().reloadConfigValues();
        getCommand("silence-wand").setExecutor(new SilenceWandCommand());
        getCommand("silence-mobs").setExecutor(new SilenceMobsCommands());
        getCommand("silence-reload").setExecutor(new SilenceReload());
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        // Plugin startup logic

    }

    public static ShutTheCluckUp getInstance(){
        return instance;
    }

    public static MiniMessage getMiniMessage(){
        return miniMessage;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
