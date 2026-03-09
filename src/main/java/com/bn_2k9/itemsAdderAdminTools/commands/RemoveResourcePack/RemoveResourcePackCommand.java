package com.bn_2k9.itemsAdderAdminTools.commands.RemoveResourcePack;

import com.bn_2k9.itemsAdderAdminTools.framework.Color;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class RemoveResourcePackCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        Player target = null;

        if (args.length < 1) {
            target = player;
        } else {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                target = p;
            }
        }

        player.sendMessage(Color.colorPrefix("<white>Removed resource pack for: " + target.getName()));
        target.clearResourcePacks();

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender instanceof Player && sender.hasPermission(permission());
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.RemoveResourcePack";
    }
}
