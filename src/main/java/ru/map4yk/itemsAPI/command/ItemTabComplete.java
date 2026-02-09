package ru.map4yk.itemsAPI.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.map4yk.itemsAPI.ItemsAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemTabComplete implements TabCompleter {
    private final ItemsAPI plugin;

    public ItemTabComplete(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (String subCMD : Arrays.asList("give", "add")) {
                if (subCMD.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(subCMD);
                }
            }
            return completions;
        }

        String subCommand = args[0].toLowerCase();
        if (subCommand.equals("give")) {
            if (args.length == 2) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            } else if (args.length == 3) {
                return plugin.getItemStorage().getAllItems().keySet().stream()
                        .filter(key -> key.toLowerCase().startsWith(args[2].toLowerCase()))
                        .sorted().collect(Collectors.toList());
            }
        }
        return null;
    }
}
