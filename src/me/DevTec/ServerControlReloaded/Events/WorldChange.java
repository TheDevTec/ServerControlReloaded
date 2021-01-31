package me.DevTec.ServerControlReloaded.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import me.DevTec.ServerControlReloaded.SCR.API;
import me.DevTec.ServerControlReloaded.Utils.SPlayer;
import me.DevTec.ServerControlReloaded.Utils.setting;
import me.devtec.theapi.scheduler.Scheduler;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.reflections.Ref;

public class WorldChange implements Listener {

	Map<String, Integer> sleepTask = new HashMap<>();
	Map<String, List<Player>> perWorldSleep = new HashMap<>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSleep(PlayerBedEnterEvent e) {
		if (setting.singeplayersleep && e.getBedEnterResult()==BedEnterResult.OK) {
			World w = e.getBed().getWorld();
			List<Player> s = perWorldSleep.getOrDefault(w.getName(), new ArrayList<>());
			s.add(e.getPlayer());
			perWorldSleep.put(w.getName(), s);
			if(!sleepTask.containsKey(w.getName())) {
				sleepTask.put(w.getName(), new Tasker() {
					long start = w.getTime();
					boolean doNight = false;
					
					public void run() {
						for(Player s : perWorldSleep.get(w.getName())) {
							int old = (int) Ref.get(Ref.player(s), "sleepTicks");
							if(old >= 98)
								Ref.set(Ref.player(s), "sleepTicks", 98);
						}
						
						if(w.getTime() >= 24000) {
							start=0;
							doNight=true;
						}
						if(doNight && w.getTime() >= 500) {
							w.setThundering(false);
							w.setStorm(false);
							cancel();
						}
						
						w.setTime(start);
						start+=50;
					}
				}.runRepeatingSync(0, 1));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSleep(PlayerBedLeaveEvent e) {
		if (setting.singeplayersleep) { //remove cache and stop task
			perWorldSleep.get(e.getBed().getWorld().getName()).remove(e.getPlayer());
			if(perWorldSleep.get(e.getBed().getWorld().getName()).isEmpty()) {
				perWorldSleep.remove(e.getBed().getWorld().getName());
			if(sleepTask.containsKey(e.getBed().getWorld().getName())) {
				Scheduler.cancelTask(sleepTask.get(e.getBed().getWorld().getName()));
				sleepTask.remove(e.getBed().getWorld().getName());
			}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void OnPlayerWorldChangeEvent(PlayerChangedWorldEvent e) {
		SPlayer a = API.getSPlayer(e.getPlayer());
		a.createEconomyAccount();
		if (a.hasFlyEnabled())
			a.enableFly();
		if (a.hasTempFlyEnabled())
			a.enableTempFly();
		if (a.hasGodEnabled())
			a.enableGod();
		a.setGamamode();

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChangeGamamode(PlayerGameModeChangeEvent e) {
		SPlayer a = API.getSPlayer(e.getPlayer());
		if (a.hasFlyEnabled())
			a.enableFly();
		if (a.hasTempFlyEnabled())
			a.enableTempFly();
		if (a.hasGodEnabled())
			a.enableGod();
	}
}