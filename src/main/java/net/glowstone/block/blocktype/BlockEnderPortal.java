package net.glowstone.block.blocktype;

import java.util.Collection;
import java.util.logging.Level;

import net.glowstone.EventFactory;
import net.glowstone.GlowServer;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowPlayer;

import org.bukkit.Achievement;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BlockEnderPortal extends BlockNeedsAttached {

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
		EntityPortalEnterEvent portalEvent = EventFactory.callEvent(new EntityPortalEnterEvent(entity, block.getLocation()));
		
		Location previousLocation = entity.getLocation().clone();
		boolean success = false;

		if (entity.getWorld().getEnvironment() == World.Environment.THE_END) {
			success = checkAndTeleportToOverworld(entity);
		} else {
			success = checkAndTeleportToEnd(entity);
		}
		
		GlowServer.logger.log(Level.INFO, "EnderPortal onTouch - Tele Success: " + success);
		if (success) {
			EntityPortalExitEvent event = EventFactory.callEvent(new EntityPortalExitEvent(entity, previousLocation, entity.getLocation().clone(), entity.getVelocity().clone(), new Vector()));
			if (!event.getAfter().equals(entity.getVelocity())) {
				entity.setVelocity(event.getAfter());
			}
		}
	}

	/**
	 * Check with EventFactory and then call teleport if event wasn't cancelled.
	 * 
	 * @param entity
	 *            Entity to teleport
	 * @return {@code true} if teleport was successful.
	 */

	public boolean checkAndTeleportToOverworld(GlowEntity entity) {
		Location target = entity.getServer().getWorlds().get(0).getSpawnLocation();

		if (entity instanceof GlowPlayer) {
			PlayerPortalEvent event = EventFactory.callEvent(new PlayerPortalEvent((GlowPlayer) entity, entity
					.getLocation().clone(), target, null));
			if (event.isCancelled()) {
				return false;
			}
			target = event.getTo();
			((GlowPlayer) entity).awardAchievement(Achievement.THE_END, false);
		} else {
			EntityPortalEvent event = EventFactory.callEvent(new EntityPortalEvent(entity,
					entity.getLocation().clone(), target, null));
			if (event.isCancelled()) {
				return false;
			}
			target = event.getTo();
		}

		return entity.teleport(target);
	}

	public boolean checkAndTeleportToEnd(GlowEntity entity) {
		Location target = null;

		if (!entity.getServer().getAllowEnd()) {
			return false;
		}

		for (World world : entity.getServer().getWorlds()) {
			if (world.getEnvironment() == World.Environment.THE_END) {
				target = world.getSpawnLocation();
				break;
			}
		}

		if (target == null) {
			return false;
		}

		if (entity instanceof GlowPlayer) {
			PlayerPortalEvent event = EventFactory.callEvent(new PlayerPortalEvent((GlowPlayer) entity, entity
					.getLocation().clone(), target, null));
			if (event.isCancelled()) {
				return false;
			}
			target = event.getTo();
			((GlowPlayer) entity).awardAchievement(Achievement.END_PORTAL, false);
		} else {
			EntityPortalEvent event = EventFactory.callEvent(new EntityPortalEvent(entity,
					entity.getLocation().clone(), target, null));
			if (event.isCancelled()) {
				return false;
			}
			target = event.getTo();
		}

		return entity.teleport(target);
	}

}
