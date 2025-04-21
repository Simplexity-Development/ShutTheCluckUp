package simplexity.shutthecluckup;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.shutthecluckup.commands.ShushCommand;
import simplexity.shutthecluckup.commands.ShushReload;
import simplexity.shutthecluckup.commands.ShushWandCommand;
import simplexity.shutthecluckup.configs.ConfigHandler;
import simplexity.shutthecluckup.listeners.InteractListener;


public final class ShutTheCluckUp extends JavaPlugin {

    private static ShutTheCluckUp instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        ConfigHandler.getInstance().reloadConfigValues();
        getCommand("shushwand").setExecutor(new ShushWandCommand());
        getCommand("shush").setExecutor(new ShushCommand());
        getCommand("shush-reload").setExecutor(new ShushReload());
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }

    public static ShutTheCluckUp getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
