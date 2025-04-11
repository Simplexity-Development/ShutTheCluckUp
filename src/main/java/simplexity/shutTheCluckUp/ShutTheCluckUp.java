package simplexity.shutTheCluckUp;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShutTheCluckUp extends JavaPlugin {

    private static ShutTheCluckUp instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        ConfigHandler.getInstance().reloadConfigValues();
        getCommand("silence-wand").setExecutor(new SilenceWand());
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
