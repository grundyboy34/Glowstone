package net.glowstone.block.itemtype;

import net.glowstone.EventFactory;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.ItemTable;
import net.glowstone.block.blocktype.BlockTNT;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemFlintAndSteel extends ItemTool {

    public ItemFlintAndSteel() {
        super(Material.FLINT_AND_STEEL.getMaxDurability());
    }

    @Override
    public boolean onToolRightClick(GlowPlayer player, ItemStack holding, GlowBlock target, BlockFace face, Vector clickedLoc) {
        switch (target.getType()) {
            case TNT:
                fireTnt(target);
                return true;
            case OBSIDIAN:
               return fireNetherPortal(player, holding, target, face, clickedLoc);
            // TODO: check for non-flammable blocks
            default:
                return setBlockOnFire(player, target, face, holding, clickedLoc);
        }
    }

    private boolean fireNetherPortal(GlowPlayer player, ItemStack holding, GlowBlock target, BlockFace face, Vector clickedLoc) {
        GlowBlock faceBlock = target.getRelative(face);

	    faceBlock.setType(Material.PORTAL);
        return true;
      /*  if (faceBlock.getType() == Material.AIR) {
        	  ItemTable.instance().getBlock(Material.DIRT).rightClickBlock(player, target, BlockFace.UP, holding.clone(), clickedLoc);
        	  return true;
        }
        return false;*/
    }

    private void fireTnt(GlowBlock tnt) {
        BlockTNT.igniteBlock(tnt, false);
    }

    private boolean setBlockOnFire(GlowPlayer player, GlowBlock clicked, BlockFace face, ItemStack holding, Vector clickedLoc) {
        GlowBlock fireBlock = clicked.getRelative(face);
        if (fireBlock.getType() != Material.AIR) {
            return true;
        }

        BlockIgniteEvent event = EventFactory.callEvent(new BlockIgniteEvent(fireBlock, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player, null));
        if (event.isCancelled()) {
            player.setItemInHand(holding);
            return false;
        }

        // clone holding to avoid decreasing of the item's amount
        ItemTable.instance().getBlock(Material.FIRE).rightClickBlock(player, clicked, BlockFace.UP, holding.clone(), clickedLoc);

        return true;
    }
}
