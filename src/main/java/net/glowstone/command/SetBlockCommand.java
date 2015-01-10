package net.glowstone.command;

import java.util.Arrays;

import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class SetBlockCommand extends BukkitCommand {

	public SetBlockCommand() {
		super("setblock");
		this.description = "setblock to given material";
		this.usageMessage = "/setblock <material | materialId> [byteData]";
		this.setAliases(Arrays.<String> asList());
		this.setPermission("glowstone.command.setblock");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.getName().equalsIgnoreCase("Console") && args.length >= 1) {
			GlowPlayer player = (GlowPlayer) sender;
			if (player != null) {
				GlowBlock targetBlock = (GlowBlock) player.getTargetBlock(null, 3);
				if (targetBlock == null) {
					return false;
				}
				byte data = -1;
				int matId = -1;
				Material mat = null;

				try {
					matId = Integer.parseInt(args[0]);
				} catch (NumberFormatException numE) {
					mat = Material.matchMaterial(args[0]);
				}
				
				if (matId < 0 && mat == null) {
					return false;
				}

				if (args.length > 1) {
					try {
						data = Byte.parseByte(args[1]);
					} catch (NumberFormatException numE) {
						
					}
				}
				
				if (data > -1) {
					targetBlock.setTypeIdAndData(matId > -1 ? matId : mat.getId(), data, true);
					return true;
				} else {
					targetBlock.setTypeId(matId > -1 ? matId : mat.getId(), true);
					return true;
				}
			}
		}
		return false;
	}
}
