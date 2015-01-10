package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.entity.objects.GlowItem;

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;

public class BlockFire extends BlockNeedsAttached {

    @Override
    public boolean canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        return true;
    }

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        super.placeBlock(player, state, face, holding, clickedLoc);
        state.setRawData((byte) 0);
    }

    @Override
    public Collection<ItemStack> getDrops(GlowBlock block, ItemStack tool) {
        return BlockDropless.EMPTY_STACK;
    }
    
	@Override
	public void onTouch(GlowEntity entity, GlowBlock block) {
		// GlowServer.logger.log(java.util.logging.Level.WARNING, "onTouch - " +
		// block.getType().name());
		if (entity instanceof GlowItem) {
			GlowItem item = (GlowItem) entity;
			if (item != null) {
				item.remove();
				return;
			}
		}
		if (entity instanceof GlowLivingEntity) {
			GlowLivingEntity livingEntity = (GlowLivingEntity) entity;
			if (livingEntity != null) {
				if (livingEntity.canTakeDamage(EntityDamageEvent.DamageCause.CONTACT)) {
					livingEntity.damage(1, EntityDamageEvent.DamageCause.CONTACT);
					livingEntity.setFireTicks(10);
					return;
				}
			}
		}

	}
}
