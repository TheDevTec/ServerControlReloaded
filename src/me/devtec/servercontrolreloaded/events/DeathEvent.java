package me.devtec.servercontrolreloaded.events;

import me.devtec.servercontrolreloaded.scr.API;
import me.devtec.servercontrolreloaded.scr.Loader;
import me.devtec.servercontrolreloaded.scr.API.TeleportLocation;
import me.devtec.servercontrolreloaded.utils.setting;
import me.devtec.servercontrolreloaded.utils.setting.DeathTp;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.punishmentapi.PunishmentAPI;
import me.devtec.theapi.scheduler.Tasker;
import me.devtec.theapi.utils.datakeeper.User;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			User s = TheAPI.getUser(e.getEntity().getKiller());
			s.setAndSave("Kills", s.getInt("Kills") + 1);
		}
		Player p = e.getEntity();
		API.setBack(p);
		if (p.hasPermission("SCR.Other.KeepInv")) {
			e.setKeepInventory(true);
			e.getDrops().clear();
		}
		if (p.hasPermission("SCR.Other.KeepExp")) {
			e.setKeepLevel(true);
			e.setDroppedExp(0);
		}
		User s = TheAPI.getUser(p);
		s.setAndSave("Deaths", s.getInt("Deaths") + 1);
		Object o = Loader.events.get("onDeath.Messages");
		if(o!=null) {
			if(o instanceof Collection) {
			for(Object fa : (Collection<?>)o) {
				if(fa!=null)
				TheAPI.msg(OnPlayerJoin.replaceAll(fa+"",p),p);
			}}else
				if(!(""+o).isEmpty())
					TheAPI.msg(OnPlayerJoin.replaceAll(""+o, p),p);
		}

		new Tasker() {
			public void run() {
		Object o = Loader.events.get("onDeath.Commands");
		if(o!=null) {
			if(o instanceof Collection) {
			for(Object fa : (Collection<?>)o) {
				if(fa!=null)
					TheAPI.sudoConsole(TheAPI.colorize(OnPlayerJoin.replaceAll(""+fa, p)));
			}}else
				if(!(""+o).isEmpty())
					TheAPI.sudoConsole(TheAPI.colorize(OnPlayerJoin.replaceAll(""+o, p)));
		}}
		}.runTaskSync();
		o = Loader.events.get("onDeath.Broadcast");
		if(o!=null) {
			if(o instanceof Collection) {
			for(Object fa : (Collection<?>)o) {
				if(fa!=null)
				TheAPI.bcMsg(OnPlayerJoin.replaceAll(fa+"",p));
			}}else
				if(!(""+o).isEmpty())
					TheAPI.bcMsg(OnPlayerJoin.replaceAll(""+o, p));
		}
		
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void Respawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		try {
		if (PunishmentAPI.getBanList(p.getName()).isJailed()
				|| PunishmentAPI.getBanList(p.getName()).isTempJailed())
			e.setRespawnLocation((Location) Loader.config.get("Jails." + TheAPI.getUser(p).getString("Jail.Location")));
		else if (setting.deathspawnbol) {
			if (setting.deathspawn == DeathTp.HOME)
				e.setRespawnLocation(API.getTeleportLocation(p, TeleportLocation.HOME));
			else if (setting.deathspawn == DeathTp.BED)
				e.setRespawnLocation(API.getTeleportLocation(p, TeleportLocation.BED));
			else if (setting.deathspawn == DeathTp.SPAWN) {
				e.setRespawnLocation(API.getTeleportLocation(p, TeleportLocation.SPAWN));
				Loader.sendMessages(p, "Spawn.Teleport.You");
			}
		}}catch(Exception eere) {}
	}
}
