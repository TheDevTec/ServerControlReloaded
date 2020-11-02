package me.DevTec.ServerControlReloaded.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import me.DevTec.ServerControlReloaded.SCR.Loader;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.ConfigAPI.Config;
import me.DevTec.TheAPI.PlaceholderAPI.PlaceholderAPI;
import me.DevTec.TheAPI.ScoreboardAPI.ScoreboardAPI;
import me.DevTec.TheAPI.ScoreboardAPI.ScoreboardType;

public class ScoreboardStats {
	
	static HashMap<String, ScoreboardAPI> setup = new HashMap<>(); //prevent of recreating unused scoreboards

	public static void createScoreboard(Player p) {
		if (!setup.containsKey(p.getName()))
			setup.put(p.getName(), TheAPI.getScoreboardAPI(p, ScoreboardType.PACKETS));
		ScoreboardAPI a = setup.get(p.getName());
		Config f = Loader.sb;
		String s = f.getString("PerWorld." + p.getWorld().getName() + ".Name");
		if(s==null)
			s = f.getString("PerPlayer." + p.getName() + ".Name");
		if(s==null)
			s = f.getString("Name");

		List<String> b = f.getStringList("PerWorld." + p.getWorld().getName() + ".Lines");
		if(!f.exists("PerWorld." + p.getWorld().getName() + ".Lines"))
			b = f.getStringList("PerPlayer." + p.getName() + ".Lines");
		if(!f.exists("PerPlayer." + p.getName() + ".Lines"))
			b = f.getStringList("Lines");
		a.setDisplayName(s);
		int line = b.size();
		for (String ss : b)
			a.setLine(--line,PlaceholderAPI.setPlaceholders(p, TabList.replace(ss, p)));
	}
	
	public static void removeScoreboard() {
		for (Entry<String, ScoreboardAPI> p : setup.entrySet())
			p.getValue().destroy();
		setup.clear();
	}

	public static void removeScoreboard(Player p) {
		if(setup.containsKey(p.getName()))
		setup.get(p.getName()).destroy();
		setup.remove(p.getName());
	}
}