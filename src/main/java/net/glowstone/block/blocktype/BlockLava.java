package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.objects.GlowItem;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockLava extends BlockLiquid {

    public BlockLava() {
        super(Material.LAVA_BUCKET);
    }

    @Override
    public boolean isCollectible(GlowBlockState target) {
        return target.getType() == Material.STATIONARY_LAVA || (target.getType() == Material.LAVA && target.getRawData() == 8);
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
					livingEntity.damage(4, EntityDamageEvent.DamageCause.CONTACT);
					entity.setFireTicks(10);
					return;
				}
			}
		}

	}

}
