package simplexity.shutthecluckup.configs;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import simplexity.shutthecluckup.ShutTheCluckUp;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("UnstableApiUsage")
public class ConfigHandler {

    private static ConfigHandler instance;

    public ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }

    private final Logger logger = ShutTheCluckUp.getInstance().getLogger();
    private final MiniMessage miniMessage = ShutTheCluckUp.getMiniMessage();
    private ItemStack wandItemStack;
    private final HashSet<EntityType> enabledMobs = new HashSet<>();
    private final HashSet<String> enabledMobsNames = new HashSet<>();
    private double maxRadius;

    public void reloadConfigValues() {
        ShutTheCluckUp.getInstance().reloadConfig();
        FileConfiguration config = ShutTheCluckUp.getInstance().getConfig();
        List<String> mobList = config.getStringList("mobs");
        maxRadius = config.getInt("max-radius", 10);
        validateEntityTypes(mobList, enabledMobs, enabledMobsNames);
        validateSilenceWand(config);
    }

    private void validateEntityTypes(List<String> stringList, HashSet<EntityType> entitySet, HashSet<String> nameSet) {
        entitySet.clear();
        if (stringList == null || stringList.isEmpty()) return;
        for (String entity : stringList) {
            try {
                EntityType entityType = EntityType.valueOf(entity);
                entitySet.add(entityType);
                nameSet.add(entityType.name());

            } catch (IllegalArgumentException e) {
                logger.warning("The entity type '" + entity + "' was not found. Please check that your syntax and make sure you use SPACE and not TAB");
            }
        }
    }

    private void validateSilenceWand(FileConfiguration config) {
        String customNameString = config.getString("silence-wand.custom-name", "<yellow>Silencing Wand</yellow>");
        String itemTypeString = config.getString("silence-wand.item-type", "STICK");
        String itemModelString = config.getString("silence-wand.item-model");
        boolean enchantmentGlint = config.getBoolean("silence-wand.enchantment-glint", true);
        Component customName = miniMessage.deserialize(customNameString);
        Material itemType = Material.getMaterial(itemTypeString);
        if (itemType == null) {
            logger.warning("The item type '" + itemTypeString + "' is invalid, please choose an item type from https://jd.papermc.io/paper/1.21.5/org/bukkit/inventory/ItemType.html");
            logger.warning("Setting item type to 'STICK' until a valid type is supplied");
            itemType = Material.STICK;
        }
        ItemStack wandItem = ItemStack.of(itemType);
        wandItem.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, enchantmentGlint);
        wandItem.setData(DataComponentTypes.CUSTOM_NAME, customName);
        wandItem = setupWandItem(itemModelString, wandItem);
        wandItemStack = wandItem;
    }

    private ItemStack setupWandItem(String itemModelString, ItemStack wandItem) {
        if (itemModelString == null || itemModelString.isEmpty() || itemModelString.equals("[]")) return wandItem;
        String[] split = itemModelString.split(":");
        if (split.length != 2) {
            logger.warning(itemModelString + " is not a valid item model, these must be declared as \"namespace:location\", please check your syntax");
            return wandItem;
        }
        NamespacedKey key = new NamespacedKey(split[0], split[1]);
        wandItem.setData(DataComponentTypes.ITEM_MODEL, key);
        return wandItem;
    }

    public ItemStack getWandItemStack() {
        return wandItemStack;
    }

    public HashSet<EntityType> getEnabledMobs(){
        return enabledMobs;
    }

    public double getMaxRadius() {
        return maxRadius;
    }
}
