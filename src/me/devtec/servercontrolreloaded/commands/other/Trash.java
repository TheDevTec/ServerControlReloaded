package me.devtec.servercontrolreloaded.commands.other;

import me.devtec.servercontrolreloaded.scr.Loader;
import me.devtec.theapi.guiapi.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Trash implements CommandExecutor, TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1,
			String arg2, String[] arg3) {
		return Arrays.asList();
	}

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (Loader.has(s, "Trash", "Other")) {
			if (s instanceof Player)
				Trash.s.open((Player) s);
			return true;
		}
		Loader.noPerms(s, "Trash", "Other");
		return true;
	}
	
	public static GUI s;
}