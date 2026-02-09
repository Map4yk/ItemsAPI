package ru.map4yk.itemsAPI.storage;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import ru.map4yk.itemsAPI.ItemsAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemStorage {
    private final ItemsAPI plugin;
    @Getter private final HashMap<String, ItemStack> cacheItems = new HashMap<>();

    public ItemStorage(ItemsAPI plugin) {
        this.plugin = plugin;
        this.loadItems();
    }

    public void loadItems() {
        cacheItems.clear();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("items");
        if (section == null) { return; }

        for (String itemKey : section.getKeys(false)) {
            ItemStack itemStack = section.getItemStack(itemKey);
            if (itemStack != null) {
                cacheItems.put(itemKey, itemStack.clone());
            }
        }

        plugin.getLogger().info("Предметы загружены!");
    }

    public ItemStack getItem(String itemKey) {
        ItemStack itemStack = cacheItems.get(itemKey);
        return itemStack != null ? itemStack.clone() : null;
    }

    public Map<String, ItemStack> getAllItems() {
        Map<String, ItemStack> result = new ConcurrentHashMap<>(cacheItems.size());
        cacheItems.forEach((itemKey, itemStack) -> result.put(itemKey, itemStack.clone()));
        return result;
    }

    public void addItems(Map<String, ItemStack> items) {
        items.forEach((key, item) -> cacheItems.put(key, item.clone()));

        plugin.getConfig().set("items", null);
        ConfigurationSection section = plugin.getConfig().createSection("items");
        cacheItems.forEach(section::set);
        plugin.saveConfig();
    }
}
