package Events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.lapismc.afkplus.api.AFKStartEvent;
import net.lapismc.afkplus.api.AFKStopEvent;
import net.lapismc.afkplus.playerdata.AFKPlusPlayer;

public class AFKPlus implements Listener {
	public static HashMap<Player,AFKPlusPlayer> AFKPlus = new HashMap<Player,AFKPlusPlayer>();
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(AFKStartEvent e) {
		if(!AFKPlus.containsKey(Bukkit.getPlayer(e.getPlayer().getName())))AFKPlus.put(Bukkit.getPlayer(e.getPlayer().getName()),e.getPlayer());
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(AFKStopEvent e) {
		if(AFKPlus.containsKey(Bukkit.getPlayer(e.getPlayer().getName())))AFKPlus.remove(Bukkit.getPlayer(e.getPlayer().getName()));
	}
}