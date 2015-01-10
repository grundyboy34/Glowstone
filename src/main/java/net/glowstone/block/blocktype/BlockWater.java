package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.objects.GlowItem;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockWater extends BlockLiquid {

	public BlockWater() {
		super(Material.WATER_BUCKET);
	}

	@Override
	public boolean isCollectible(GlowBlockState target) {
		return target.getType() == Material.STATIONARY_WATER
				|| (target.getType() == Material.WATER && target.getRawData() == 8);
	}

	@Override
	public void onTouch(GlowEntity entity, GlowBlock block) {
		if (entity != null && entity.getFireTicks() > 0) {
			entity.setFireTicks(0);
		}
	}

}
