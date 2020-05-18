package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import ServerControl.API;
import ServerControl.Loader;
import Utils.MultiWorldsUtils;

public class Chunks implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		if (API.hasPerm(s, "ServerControl.Chunks")) {
			if (cmd.getName().equalsIgnoreCase("Chunks")) {
				if (args.length == 0) {
					Loader.msg(Loader.s("Prefix") + "&e----------------- &bChunks &e-----------------", s);
					Loader.msg("", s);
					int chunks = 0;
					for (World w : Bukkit.getWorlds()) {
						chunks = chunks + w.getLoadedChunks().length;
						Loader.msg(Loader.s("Prefix") + Loader.s("Chunks.Loaded").replace("%world%", w.getName())
								.replace("%chunks%", String.valueOf(w.getLoadedChunks().length)), s);
					}
					Loader.msg(Loader.s("Prefix") + Loader.s("Chunks.TotalLoaded")
							.replace("%worlds%", String.valueOf(Bukkit.getWorlds().size()))
							.replace("%chunks%", String.valueOf(chunks)), s);
					return true;
				}
				if (args[0].equalsIgnoreCase("Unload")) {
					Loader.msg(Loader.s("Prefix") + "&e----------------- &bChunks &e-----------------", s);
					Loader.msg("", s);
					MultiWorldsUtils.unloadWorlds(s);
					return true;
				}
			}
		}
		return true;
	}

	private static final List<String> Unload = Arrays.asList("Unload");

	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args) {
		List<String> c = new ArrayList<>();
		if (cmd.getName().equalsIgnoreCase("chunks") && args.length == 1) {
			if (s.hasPermission("ServerControl.Chunks")) {
				c.addAll(StringUtil.copyPartialMatches(args[0], Unload, new ArrayList<>()));
			}
		}
		return c;
	}
}