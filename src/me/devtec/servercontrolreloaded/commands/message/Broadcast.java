package me.devtec.servercontrolreloaded.commands.message;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.devtec.servercontrolreloaded.commands.CommandsManager;
import me.devtec.servercontrolreloaded.scr.API;
import me.devtec.servercontrolreloaded.scr.Loader;
import me.devtec.servercontrolreloaded.scr.Loader.Placeholder;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class Broadcast implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (Loader.has(s, "Broadcast", "Message")) {
			if(!CommandsManager.canUse("Message.Broadcast", s)) {
				Loader.sendMessages(s, "Cooldowns.Commands", Placeholder.c().add("%time%", StringUtils.timeToString(CommandsManager.expire("Message.Broadcast", s))));
				return true;
			}
			if (args.length == 0) {
				Loader.Help(s, "Broadcast", "Message");
				return true;
			}
			String msg = TheAPI.buildString(args);
			TheAPI.broadcastMessage(Loader.config.getString("Format.Broadcast").replace("%sender%", s.getName())
					.replace("%message%", msg));
			return true;
		}
		Loader.noPerms(s, "Broadcast", "Message");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		return StringUtils.copyPartialMatches(args[args.length-1], API.getPlayerNames(s));
	}
}
