package ru.map4yk.itemsAPI;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.map4yk.itemsAPI.api.ItemsAPIInterface;
import ru.map4yk.itemsAPI.api.ItemsAPIProvider;
import ru.map4yk.itemsAPI.command.ItemCommand;
import ru.map4yk.itemsAPI.command.ItemTabComplete;
import ru.map4yk.itemsAPI.storage.ItemStorage;

public final class ItemsAPI extends JavaPlugin {
    @Getter private ItemStorage itemStorage;
    private ItemsAPIInterface pluginAPI;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.itemStorage = new ItemStorage(this);
        this.pluginAPI = ItemsAPIProvider.getAPI();

        PluginCommand command = getCommand("items");
        if (command != null) {
            ItemCommand executor = new ItemCommand(this);
            TabCompleter completer = new ItemTabComplete(this);

            command.setExecutor(executor);
            command.setTabCompleter(completer);
        }
    }
}
