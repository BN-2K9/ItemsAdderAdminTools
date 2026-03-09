package com.bn_2k9.itemsAdderAdminTools.commands.ScanSelection;

import com.bn_2k9.itemsAdderAdminTools.ItemsAdderAdminTools;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.Region;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class ScanSelectionCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getSender();

        Bukkit.getScheduler().runTaskAsynchronously(ItemsAdderAdminTools.getInstance(), () -> {

            Actor actor = BukkitAdapter.adapt(commandSourceStack.getSender());

            // Get the selection of the player.
            Region selection = null;
            try {
                selection = WorldEdit.getInstance().getSessionManager().get(actor).getSelection();
            } catch (IncompleteRegionException e) {
                throw new RuntimeException(e);
            }

            // Make sure a player has a selection.
            if (selection == null) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Make a selection first."));
            }

            final World bukkitWorld = BukkitAdapter.adapt(selection.getWorld());

            // Iterating through the blocks in a region.
            selection.iterator().forEachRemaining(blockVector3 -> {

                BlockState state = bukkitWorld.getBlockAt(BukkitAdapter.adapt(bukkitWorld, blockVector3)).getState();

                if (state instanceof InventoryHolder) {

                    InventoryHolder inventoryHolder = (InventoryHolder) state;

                    for (ItemStack itemStack : inventoryHolder.getInventory().getContents()) {
                        if (itemStack != null && itemStack.getType() != Material.AIR) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Found item: " + itemStack.getType().toString() + " at: x:" + state.getLocation().getBlockX() + " y: " + state.getLocation().getBlockY() + " z: " + state.getLocation().getBlockZ()));
                        }
                    }

                }

            });

        });
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender instanceof Player && sender.hasPermission(permission());
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.ScanSelection";
    }
}
