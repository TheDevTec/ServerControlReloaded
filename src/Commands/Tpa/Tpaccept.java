package Commands.Tpa;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ServerControl.API;
import ServerControl.Loader;
import Utils.setting;
import me.Straiker123.TheAPI;

public class Tpaccept implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender p, Command arg1, String arg2, String[] args) {
		if (p.hasPermission("ServerControl.Tpa") || p.hasPermission("ServerControl.Tpahere")) {
			if (p instanceof Player) {
				if (args.length == 0) {
					String pd = RequestMap.getRequest(p.getName());
					if (pd == null || TheAPI.getPlayer(pd) == null
							|| pd != null && !RequestMap.containsRequest(p.getName(), pd)) {
						Loader.msg(Loader.s("Prefix") + Loader.s("TpaSystem.NoRequest"), p);
						return true;
					}
					Player d = TheAPI.getPlayer(pd);
					switch (RequestMap.getTeleportType(p.getName(), pd)) {
					case TPA:
						API.setBack(d);
						Loader.msg(Loader.s("Prefix")
								+ Loader.s("TpaSystem.Tpaccept").replace("%player%", pd).replace("%playername%", pd),
								p);
						if (setting.tp_safe)
							TheAPI.getPlayerAPI(d).safeTeleport(((Player) p).getLocation().add(0, -1, 0));
						else
							d.teleport(((Player) p).getLocation());
						Loader.msg(Loader.s("Prefix") + Loader.s("TpaSystem.TpaAccepted")
								.replace("%player%", p.getName()).replace("%playername%", p.getName()), d);
						RequestMap.removeRequest(p.getName(), pd);
						break;
					case TPAHERE:
						API.setBack((Player) p);
						Loader.msg(Loader.s("Prefix") + Loader.s("TpaSystem.Tpahereccept").replace("%player%", pd)
								.replace("%playername%", pd), p);
						Location loc = d.getLocation();
						if (setting.tp_onreqloc && RequestMap.getLocation(p.getName(), pd) != null)
							loc = RequestMap.getLocation(p.getName(), pd);
						if (setting.tp_safe)
							TheAPI.getPlayerAPI((Player) p).safeTeleport(loc.add(0, -1, 0));
						else
							((Player) p).teleport(loc);
						Loader.msg(Loader.s("Prefix") + Loader.s("TpaSystem.TpahereAccepted")
								.replace("%player%", p.getName()).replace("%playername%", pd), p);
						RequestMap.removeRequest(p.getName(), pd);
						break;
					}
					return true;
				}
				return true;
			}
			Loader.msg(Loader.s("ConsoleErrorMessage"), p);
			return true;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		return c;
	}
}