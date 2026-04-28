package com.bn_2k9.itemsAdderAdminTools.commands.RemoveResourcePack;

import com.bn_2k9.itemsAdderAdminTools.framework.Color;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

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

        if (target == null){
            player.sendMessage(Color.colorPrefix("<red>Can't find a vailid target."));
            return;
        }

        player.sendMessage(Color.colorPrefix("<white>Removed resource pack for: " + target.getName()));
        target.clearResourcePacks();

    }

    @Override
    public @NonNull Collection<String> suggest(@NonNull CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(@NonNull CommandSender sender) {
        return sender instanceof Player && sender.hasPermission(Objects.requireNonNull(permission()));
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.RemoveResourcePack";
    }
}
