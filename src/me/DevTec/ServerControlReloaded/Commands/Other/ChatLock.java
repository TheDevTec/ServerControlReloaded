package me.DevTec.ServerControlReloaded.Commands.Other;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.DevTec.ServerControlReloaded.SCR.Loader;
import me.DevTec.ServerControlReloaded.Utils.setting;

public class ChatLock implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (Loader.has(s, "ChatLock", "Other")) {
			if (!setting.lock_chat) {
				Loader.sendBroadcasts(s, "ChatLock.Lock");
				Loader.config.set("Options.ChatLock", true);
				Loader.config.save();
				setting.lock_chat = true;
				return true;
			}
			Loader.sendBroadcasts(s, "ChatLock.Unlock");
			Loader.config.set("Options.ChatLock", false);
			Loader.config.save();
			setting.lock_chat = false;
			return true;
		}
		Loader.noPerms(s, "ChatLock", "Other");
		return true;
	}
}