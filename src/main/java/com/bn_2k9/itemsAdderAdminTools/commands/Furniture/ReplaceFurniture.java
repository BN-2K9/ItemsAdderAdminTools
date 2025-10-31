package com.bn_2k9.itemsAdderAdminTools.commands.Furniture;

import com.bn_2k9.itemsAdderAdminTools.framework.Color;
import com.bn_2k9.itemsAdderAdminTools.framework.ItemsAdder.ItemsAdderCache;
import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import dev.lone.itemsadder.api.CustomFurniture;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class ReplaceFurniture implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        if (args.length > 1) {

            if (!CustomFurniture.isInRegistry(args[0])) {
                player.sendMessage(Color.colorPrefix("<red><old furniture> doesn't exist."));
                return;
            }

            if (!CustomFurniture.isInRegistry(args[1])) {
                player.sendMessage(Color.colorPrefix("<red><new furniture> doesn't exist."));
                return;
            }

            com.sk89q.worldedit.entity.Player actor = BukkitAdapter.adapt(player);
            try {
                if (WorldEdit.getInstance().getSessionManager().get(actor).getSelection() == null) {
                    player.sendMessage(Color.colorPrefix("<red>You don't have a active worldedit selection."));
                    return;
                }
            } catch (IncompleteRegionException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage(Color.colorPrefix("<gold>Operation started..... "));

            Region selection = null;
            try {
                selection = WorldEdit.getInstance().getSessionManager().get(actor).getSelection();
            } catch (IncompleteRegionException e) {
                throw new RuntimeException(e);
            }

            final int[] BlocksChanged = {0};
            selection.forEach(blockVector3 -> {
                Location location = new Location(player.getWorld(), blockVector3.x(), blockVector3.y(), blockVector3.z());
                CustomFurniture customFurniture = CustomFurniture.byAlreadySpawned(player.getWorld().getBlockAt(location));

                if (customFurniture != null) {
                    if (customFurniture.getNamespacedID().equals(args[0])) {
                        customFurniture.replaceFurniture(args[1]);
                        Logger.log(Logger.LogType.DEBUG, "Furniture replaced at: X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ());
                    }
                }
            });

            player.sendMessage(Color.colorPrefix("<green>Operation done! Blocks changed: " + BlocksChanged[0]));

        } else {
            player.sendMessage(Color.colorPrefix("<red>Command Syntax: /ReplaceFurnitureNear <old furniture> <new furniture>"));
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length < 2) {
            return ItemsAdderCache.getInstance().getNameSpacedFurniture();
        }
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.ReplaceFurniture";
    }
}
