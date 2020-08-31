package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ServerControl.API;
import ServerControl.Loader;
import ServerControl.SPlayer;
import Utils.Repeat;
import me.DevTec.TheAPI.TheAPI;

public class AFK implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (API.hasPerm(s, "ServerControl.AFK")) {
				if (s instanceof Player) {
					SPlayer p = API.getSPlayer((Player) s);
					if (p.isAFK()) {
						p.setAFK(false);
						if (!p.hasVanish())
							TheAPI.broadcastMessage(Loader.s("Prefix") + Loader.s("AFK.NoLongerAFK")
									.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()));
					} else {
						p.setAFK(true);
					}
					return true;
				}

				Loader.Help(s, "/AFK <player>", "AFK-Other");
				return true;
			}
			return true;
		}
		if (args.length == 1) {
			if (API.hasPerm(s, "ServerControl.AFK.Other")) {
				SPlayer p = API.getSPlayer(TheAPI.getPlayer(args[0]));
				if (p.getPlayer() != null) {
					if (p.isAFK()) {
						p.setAFK(false);
						if (!p.hasVanish())
							TheAPI.broadcastMessage(Loader.s("Prefix") + Loader.s("AFK.NoLongerAFK")
									.replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()));
					} else {
						p.setAFK(true);
					}
					return true;
				}
				if (args[0].equals("*")) {
					Repeat.a(s, "AFK *");
					return true;
				}
				TheAPI.msg(Loader.PlayerNotOnline(args[0]), s);
				return true;
			}
			return true;
		}
		return false;
	}
}
