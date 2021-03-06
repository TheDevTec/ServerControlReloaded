package me.devtec.servercontrolreloaded.commands.server;


import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.devtec.servercontrolreloaded.commands.CommandsManager;
import me.devtec.servercontrolreloaded.commands.server.BigTask.TaskType;
import me.devtec.servercontrolreloaded.scr.Loader;
import me.devtec.servercontrolreloaded.scr.Loader.Placeholder;
import me.devtec.theapi.utils.StringUtils;

public class Stop implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (Loader.has(s, "Stop", "Server")) {
			if(!CommandsManager.canUse("Server.Stop", s)) {
				Loader.sendMessages(s, "Cooldowns.Commands", Placeholder.c().add("%time%", StringUtils.timeToString(CommandsManager.expire("Server.Stop", s))));
				return true;
			}
			if (args.length == 0) {
				BigTask.start(TaskType.STOP, StringUtils.timeFromString(Loader.config.getString("Options.WarningSystem.Stop.PauseTime")));
				return true;
			}
			if (args[0].equalsIgnoreCase("cancel")) {
				BigTask.cancel(true);
				return true;
			}
			if (args[0].equalsIgnoreCase("now")) {
				BigTask.start(TaskType.STOP, 0);
				return true;
			}
			if (BigTask.r == -1)
				BigTask.start(TaskType.STOP, StringUtils.getTimeFromString(args[0]));
			return true;
		}
		Loader.noPerms(s, "Stop", "Server");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		if (Loader.has(s, "Stop", "Server") && args.length == 1)
			return StringUtils.copyPartialMatches(args[0], Arrays.asList("15s", "30s", "now", "cancel"));
		return Arrays.asList();
	}

}
