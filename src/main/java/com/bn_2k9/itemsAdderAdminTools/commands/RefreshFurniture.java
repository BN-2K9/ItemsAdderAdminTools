package com.bn_2k9.itemsAdderAdminTools.commands;

import com.bn_2k9.itemsAdderAdminTools.framework.Logger;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import dev.lone.itemsadder.api.CustomFurniture;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class RefreshFurniture implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        // Get the worldedit equivalent of a player.
        Player player = (Player) commandSourceStack.getSender();
        com.sk89q.worldedit.entity.Player actor = BukkitAdapter.adapt(player);

        // Check if the player has a selection.
        try {
            if (WorldEdit.getInstance().getSessionManager().get(actor).getSelection() == null) {
                player.sendMessage("You don't have a active worldedit selection.");
                return;
            }
        } catch (IncompleteRegionException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize("<white>ItemsAdderAdminTools <grey>>> <gold>Operation started..... "));

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
                customFurniture.replaceFurniture(customFurniture.getNamespacedID());
                Logger.log(Logger.LogType.DEBUG, "Furniture updated at: X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ());
                BlocksChanged[0]++;
            }
        });

        player.sendMessage(MiniMessage.miniMessage().deserialize("<white>ItemsAdderAdminTools <grey>>> <green>Operation done! Blocks changed: " + BlocksChanged[0]));
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.RefreshFurniture";
    }
}
