package me.DevTec.ServerControlReloaded.Commands.TpSystem;


import java.util.List;

import org.bukkit.entity.Player;

import me.DevTec.ServerControlReloaded.SCR.API;
import me.DevTec.ServerControlReloaded.SCR.Loader;
import me.DevTec.ServerControlReloaded.SCR.Loader.Placeholder;
import me.DevTec.ServerControlReloaded.Utils.setting;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.Position;
import me.devtec.theapi.utils.StringUtils;
import me.devtec.theapi.utils.datakeeper.User;
import me.devtec.theapi.utils.datakeeper.collections.UnsortedList;

/**
 * 
 * @author waskerSK
 * 
 * 19.11. 2020
 * Updated by StraikerinaCZ (DevTec)
 */
public class RequestMap {
	//Types: 0 = TPA, 1 = TPAHERE
	public static void add(Player sender, String target, int type) {
		if(has(target, sender.getName())) {
			Loader.sendMessages(sender, "TpSystem.HaveRequest", Placeholder.c().replace("%player%", target).replace("%playername%", target)
					.replace("%type%", TheAPI.getUser(sender.getUniqueId()).getInt("teleport." + target + ".b")==0?"Tpa":"Tpahere"));
			return;
		}
		if(isBlocking(target, sender.getName())) {
			Loader.sendMessages(sender, "TpSystem.Block.IsBlocked.Request", Placeholder.c().replace("%player%", target).replace("%playername%", target));
			return;
		}
		User s = TheAPI.getUser(sender);
		List<String> c = s.getStringList("tpcancel");
		c.add(target);
		s.setAndSave("tpcancel", c);
		Loader.sendMessages(sender, "TpSystem."+(type==0?"Tpa":"Tpahere")+".Send.Sender", Placeholder.c().replace("%player%", target).replace("%playername%", target));
		if(TheAPI.getPlayerOrNull(target)!=null)
		Loader.sendMessages(TheAPI.getPlayerOrNull(target), "TpSystem."+(type==0?"Tpa":"Tpahere")+".Send.Receiver", Placeholder.c().replace("%player%", sender.getName()).replace("%playername%", sender.getDisplayName()));
		User d = TheAPI.getUser(target);
		d.set("teleport." + sender.getName() + ".a", type);
		d.set("teleport." + sender.getName() + ".b", System.currentTimeMillis()/1000);
		if(type==1) //Tpahere
		if (setting.tp_onreqloc)
			d.set("teleport." + sender.getName() + ".c", new Position(sender.getLocation()).toString()); //teleport target to request position
		d.save();
	}
	
	public static boolean isBlocking(String sender, String target) {
		return TheAPI.getUser(sender).getBoolean("TpBlock." + target) || TheAPI.getUser(sender).getBoolean("TpBlock-Global");
	}
	
	public static void cancel(Player sender) {
		List<String> aw = TheAPI.getUser(sender).getStringList("tpcancel");
		if(aw.isEmpty()) {
			Loader.sendMessages(sender, "TpSystem.NoRequest");
			return;
		}else {
			String first = aw.get(0);
			int type = TheAPI.getUser(sender.getName()).getInt("teleport."+first+".a");
			remove(sender.getName(), first);
			Player a = TheAPI.getPlayerOrNull(first);
			Loader.sendMessages(sender, "TpSystem."+(type==0?"Tpa":"Tpahere")+".Cancel.Sender", Placeholder.c()
					.replace("%player%", a==null?first:a.getName()).replace("%playername%", a==null?first:a.getDisplayName()));
			if(a!=null)
			Loader.sendMessages(a, "TpSystem."+(type==0?"Tpa":"Tpahere")+".Cancel.Receiver", Placeholder.c()
					.replace("%player%", sender.getName()).replace("%playername%", sender.getDisplayName()));
		}
	}

	public static void remove(String sender, String target) {
		TheAPI.getUser(target).remove("teleport." + sender);
		TheAPI.getUser(target).save();
		User s = TheAPI.getUser(sender);
		List<String> c = s.getStringList("tpcancel");
		c.remove(target);
		s.setAndSave("tpcancel", c);
	}

	public static Player getFirst(String sender) {
		List<String> acceptable = new UnsortedList<>();
		for(String s : TheAPI.getUser(sender).getKeys("teleport")) {
			if (has(sender, s)) {
				acceptable.add(s);
			}
		}
		return acceptable.isEmpty()?null:TheAPI.getPlayerOrNull(acceptable.get(0));
	}

	public static boolean has(String sender, String target) {
		if (TheAPI.getPlayerOrNull(target) != null && (TheAPI.getUser(sender).getLong("teleport." + target + ".b") - System.currentTimeMillis()/1000 + StringUtils.timeFromString(Loader.config.getString("Options.Teleport.RequestTime"))) > 0)
			return true;
		else {
			remove(sender, target);
			return false;
		}
	}
	
	public static Position getPos(String sender, String target) {
		return Position.fromString(TheAPI.getUser(sender).getString("teleport."+target+".c"));
	}
	
	public static boolean accept(Player sender) {
		Player target = getFirst(sender.getName());
		if(target==null) {
			Loader.sendMessages(sender, "TpSystem.NoRequest");
			return false;
		}
		//Tpa
		if(TheAPI.getUser(sender).getInt("teleport."+target.getName()+".a")==0) {
			API.setBack(target);
		if (setting.tp_safe)
			API.safeTeleport(target,sender.getLocation());
		else
			target.teleport(sender);
		Loader.sendMessages(sender, "TpSystem.Tpa.Accept.Sender", Placeholder.c()
				.add("%player%", target.getName()).add("%playername%", target.getDisplayName()));
		Loader.sendMessages(target, "TpSystem.Tpa.Accept.Receiver", Placeholder.c()
				.add("%player%", sender.getName()).add("%playername%", sender.getDisplayName()));
		remove(target.getName(), sender.getName());
		return true;
		}
		//Tpahere
		API.setBack(sender);
		if (setting.tp_safe)
			if (setting.tp_onreqloc && getPos(sender.getName(), target.getName()) != null)
				API.safeTeleport(sender,getPos(sender.getName(), target.getName()).toLocation());
			else
				API.safeTeleport(sender,target.getLocation());
		else
			if (setting.tp_onreqloc && getPos(sender.getName(), target.getName()) != null)
				sender.teleport(getPos(sender.getName(), target.getName()).toLocation());
			else
				sender.teleport(target);
		Loader.sendMessages(sender, "TpSystem.Tpahere.Accept.Sender", Placeholder.c()
				.add("%player%", target.getName()).add("%playername%", target.getDisplayName()));
		Loader.sendMessages(target, "TpSystem.Tpahere.Accept.Receiver", Placeholder.c()
				.add("%player%", sender.getName()).add("%playername%", sender.getDisplayName()));
		remove(target.getName(), sender.getName());
		return true;
	}
	
	public static boolean deny(Player sender) {
		Player target = getFirst(sender.getName());
		if(target==null) {
			Loader.sendMessages(sender, "TpSystem.NoRequest");
			return false;
		}
		int type = TheAPI.getUser(sender).getInt("teleport."+target.getName()+".a");
		remove(target.getName(), sender.getName());
		Loader.sendMessages(sender, "TpSystem."+(type==0?"Tpa":"Tpahere")+".Reject.Sender", Placeholder.c()
				.add("%player%", target.getName()).add("%playername%", target.getDisplayName()));
		Loader.sendMessages(target, "TpSystem."+(type==0?"Tpa":"Tpahere")+".Reject.Receiver", Placeholder.c()
				.add("%player%", sender.getName()).add("%playername%", sender.getDisplayName()));
		return true;
	}
}