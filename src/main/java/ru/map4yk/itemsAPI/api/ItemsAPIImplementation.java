package ru.map4yk.itemsAPI.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.map4yk.itemsAPI.ItemsAPI;

import java.util.Collections;
import java.util.Map;

public class ItemsAPIImplementation implements ItemsAPIInterface {
    private final ItemsAPI plugin;

    public ItemsAPIImplementation(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    @Nullable
    public ItemStack getItem(@NotNull String itemKey) {
        if (itemKey == null) { throw new IllegalArgumentException("Item key cannot be null"); }
        return plugin.getItemStorage().getItem(itemKey);
    }

    @Override
    @NotNull
    public Map<String, ItemStack> getAllItems() {
        return Collections.unmodifiableMap(plugin.getItemStorage().getAllItems());
    }
}