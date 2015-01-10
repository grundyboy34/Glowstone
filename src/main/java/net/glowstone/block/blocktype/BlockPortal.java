package net.glowstone.block.blocktype;

import java.util.Collection;
import java.util.logging.Level;

import net.glowstone.GlowServer;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowPlayer;

import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BlockPortal extends BlockNeedsAttached {

    @Override
    public boolean canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        return false;
    }

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        super.placeBlock(player, state, face, holding, clickedLoc);
        state.setRawData((byte) 1);
    }

    @Override
    public Collection<ItemStack> getDrops(GlowBlock block, ItemStack tool) {
        return BlockDropless.EMPTY_STACK;
    }
    
	@Override
	public void onTouch(GlowEntity entity, GlowBlock block) {
		if (entity instanceof GlowPlayer) {
			GlowPlayer player = (GlowPlayer) entity;
			GlowServer.logger.log(Level.INFO, player.getDisplayName() + " touching portal!");
			//player.playSound(player.getLocation(), Sound.PORTAL_TRIGGER, 85, 0);
			//player.playEffect(player.getLocation(), Effect.SMOKE, GlowEffect.getDataValue(Effect.SMOKE, byte.class));
		}
	}
}
