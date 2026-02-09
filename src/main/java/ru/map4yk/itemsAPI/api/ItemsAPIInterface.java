package ru.map4yk.itemsAPI.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ItemsAPIInterface {
    @Nullable
    ItemStack getItem(@NotNull String itemKey);

    @NotNull
    Map<String, ItemStack> getAllItems();
}