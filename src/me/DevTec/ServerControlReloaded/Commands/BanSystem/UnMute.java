package me.DevTec.ServerControlReloaded.Commands.BanSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.DevTec.ServerControlReloaded.SCR.Loader;
import me.DevTec.ServerControlReloaded.SCR.Loader.Placeholder;
import me.DevTec.TheAPI.PunishmentAPI.PlayerBanList;
import me.DevTec.TheAPI.PunishmentAPI.PunishmentAPI;

public class UnMute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (Loader.has(s, "UnMute", "BanSystem")) {
			if (args.length == 0) {
				Loader.Help(s, "UnMute", "BanSystem");
				return true;
			}
			PlayerBanList p = PunishmentAPI.getBanList(args[0]);
			if (p.isBanned() || p.isTempBanned()) {
				PunishmentAPI.unmute(args[0]);
				Loader.sendMessages(s, "BanSystem.UnMute.Sender", Placeholder.c().replace("%operator%", s.getName())
						.replace("%playername%", args[0]).replace("%player%", args[0]));
				Loader.sendBroadcasts(s, "BanSystem.UnMute.Admins", Placeholder.c().replace("%operator%", s.getName())
						.replace("%playername%", args[0]).replace("%player%", args[0]));
				return true;
			}
			Loader.sendMessages(s, "BanSystem.Not.Muted", Placeholder.c().replace("%ip%", args[0]));
			return true;
		}
		Loader.noPerms(s, "UnMute", "BanSystem");
		return true;
	}

}