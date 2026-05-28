package com.bn_2k9.itemsAdderAdminTools.commands.ScanSelection;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.Region;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class ScanSelectionCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NonNull [] args) {

        if (!(commandSourceStack.getSender() instanceof Player player)) {
            return;
        }

        Actor actor = BukkitAdapter.adapt(commandSourceStack.getSender());

        // Get the selection of the player.
        Region selection = null;
        try {
            selection = WorldEdit.getInstance().getSessionManager().get(actor).getSelection();
        } catch (IncompleteRegionException e) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>A region should have 2 points. Not one ;)"));
        }

        // Make sure a player has a selection.
        if (selection == null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Make a selection first."));
        }

        final World bukkitWorld = BukkitAdapter.adapt(selection.getWorld());

        final boolean[] itemFound = {false};

        // Iterating through the blocks in a region.
        selection.iterator().forEachRemaining(blockVector3 -> {

            BlockState state = bukkitWorld.getBlockAt(BukkitAdapter.adapt(bukkitWorld, blockVector3)).getState();

            if (state instanceof InventoryHolder) {

                InventoryHolder inventoryHolder = (InventoryHolder) state;

                for (ItemStack itemStack : inventoryHolder.getInventory().getContents()) {
                    if (itemStack != null && itemStack.getType() != Material.AIR) {
                        String minimessage = "<green>Found item(s) at: <click:run_command:'/minecraft:teleport @s " + state.getLocation().getBlockX() + " " + state.getLocation().getBlockY() + " " + state.getLocation().getBlockZ() + "'>" + "<underlined>x:" + state.getLocation().getBlockX() + " y: " + state.getLocation().getBlockY() + " z: " + state.getLocation().getBlockZ() + "</underlined></click>";
                        player.sendMessage(MiniMessage.miniMessage().deserialize(minimessage));
                        itemFound[0] = true;
                        break;
                    }
                }

            }

        });

        if (!itemFound[0]) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>No items found."));
        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return BasicCommand.super.suggest(commandSourceStack, args);
    }

    @Override
    public @Nullable String permission() {
        return "ItemsAdderAdminTools.ScanSelection";
    }
}
