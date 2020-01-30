package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ServerControl.API;
import ServerControl.Loader;
import ServerControl.SPlayer;
import Utils.Repeat;

public class AFK implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label,String[] args) {
	if(args.length==0) {
		if(API.hasPerm(s,"ServerControl.AFK")) {
	if(s instanceof Player) {
		SPlayer p = new SPlayer((Player)s);
			if(p.isAFK()) {
			p.setAFK(false);
			}else {
			p.setAFK(true);
			}
			return true;
		}

	Loader.Help(s, "/AFK <player>", "AFK-Other");
	return true;
	}return true;}
	if(args.length==1) {
		if(API.hasPerm(s,"ServerControl.AFK")) {
		SPlayer p = new SPlayer(Bukkit.getPlayer(args[0]));
		if(p.getPlayer()!=null) {
			if(p.isAFK()) {
				p.setAFK(false);
				}else {
				p.setAFK(true);
				}
		return true;
	}
		if(args[0].equals("*")) {
			Repeat.a(s,"AFK *");
			return true;
		}
		Loader.msg(Loader.PlayerNotOnline(args[0]),s);
		return true;
	}return true;}
		return false;}}