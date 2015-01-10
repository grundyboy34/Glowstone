package net.glowstone.command;

import net.glowstone.entity.GlowPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;

/**
 * A built-in command to demonstrate all chat colors.
 */
public class TestSoundCommand extends BukkitCommand {
    
    public TestSoundCommand(String name) {
        super(name, "Test a sound", "/testsound", Arrays.<String>asList());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length != 1) {
        	return false;
        }
        
    	GlowPlayer player = (GlowPlayer) sender;
    	if (player != null && player.getPlayer() != null && !player.getName().equalsIgnoreCase("Console")) {
    		Sound sound = Sound.valueOf(args[0]);
    		if (sound == null) {
    			sender.sendMessage("Couldn't find sound " + args[0]);
    			return false;
    		}
    		player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    		sender.sendMessage("Played " + sound.name());
    	}
        return true;
    }

}
