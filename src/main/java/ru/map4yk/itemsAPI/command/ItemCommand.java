package ru.map4yk.itemsAPI.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.map4yk.itemsAPI.ItemsAPI;

import java.util.HashMap;
import java.util.Map;

public class ItemCommand implements CommandExecutor {
    private final ItemsAPI plugin;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public ItemCommand(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ItemsAPI.use")) {
            sender.sendMessage(mm.deserialize("<red>Недостаточно прав!</red>"));
            return true;
        }

        if (args.length < 1) {
            this.help(sender);
            return true;
        }

        return switch (args[0].toLowerCase()) {
            case "give" -> this.give(sender, args);
            case "add" -> this.add(sender);
            default -> {
                this.help(sender);
                yield true;
            }
        };
    }

    private void help(CommandSender sender) {
        sender.sendMessage(mm.deserialize("<red>/items give <игрок> <предмет> <white>- Выдать предмет"));
        sender.sendMessage(mm.deserialize("<red>/items add <white>- Сохранить предметы из инвентаря"));
    }

    private boolean give(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(mm.deserialize("<red>/items give <игрок> <предмет></red>"));
            return true;
        }

        String playerName = args[1];
        String itemKey = args[2];

        Player targetPlayer = Bukkit.getPlayerExact(playerName);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(mm.deserialize("<red>Игрок "+playerName+" не найден!</red>"));
            return true;
        }

        ItemStack itemStack = plugin.getItemStorage().getItem(itemKey);
        if (itemStack == null) {
            sender.sendMessage(mm.deserialize("<red>Предмет "+itemKey+" не найден!</red>"));
            return true;
        }

        giveItem(targetPlayer, itemStack);
        return true;
    }

    private void giveItem(Player player, ItemStack itemStack) {
        boolean hasEmptySlot = false;
        ItemStack[] inv = player.getInventory().getStorageContents();

        for (ItemStack stack : inv) {
            if (stack == null) {
                hasEmptySlot = true;
                break;
            }
        }

        if (!hasEmptySlot) {
            player.sendMessage(mm.deserialize("<red>Нет места в инвентаре, предмет выпал!</red>"));
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    private boolean add(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<red>Команда доступна только для игроков!</red>"));
            return true;
        }

        Map<String, ItemStack> toAdd = new HashMap<>();
        ItemStack[] inv = player.getInventory().getStorageContents();

        for (ItemStack item : inv) {
            if (item == null || item.getType().isAir()) {
                continue;
            }

            String key = this.genItemKey(item);
            toAdd.put(key, item.clone());
            sender.sendMessage(mm.deserialize("<red>Предмет</red> "+item.getItemMeta().customName()+" <red>сохранён!</red>"));
        }

        if (toAdd.isEmpty()) {
            sender.sendMessage(mm.deserialize("<red>Нет предметов в инвентаре!</red>"));
            return true;
        }

        plugin.getItemStorage().addItems(toAdd);
        return true;
    }

    private String genItemKey(ItemStack itemStack) {
        Component displayName = itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()
                ? itemStack.getItemMeta().displayName()
                : Component.text(itemStack.getType().name());

        PlainTextComponentSerializer plain = PlainTextComponentSerializer.plainText();
        return plain.serialize(displayName)
                .replaceAll("[^\\wа-яА-ЯёЁ ]", "")
                .trim()
                .replace(' ', '_')
                .toUpperCase();
    }
}
