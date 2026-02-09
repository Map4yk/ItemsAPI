package ru.map4yk.itemsAPI.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import ru.map4yk.itemsAPI.ItemsAPI;

public class ItemsAPIProvider {
    private static ItemsAPIInterface instance;

    @Nullable
    public static ItemsAPIInterface getAPI() {
        if (instance == null) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("ItemsAPI");
            if (plugin instanceof ItemsAPI itemsAPI) {
                instance = new ItemsAPIImplementation(itemsAPI);
            }
        }
        return instance;
    }
}