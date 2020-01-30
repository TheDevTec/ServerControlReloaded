package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ServerControl.API;
import ServerControl.Loader;
import Utils.Configs;
import me.Straiker123.TheAPI;

public class WalkSpeed implements CommandExecutor {

	public void speed(CommandSender s) {
		if(s instanceof Player) {
			Loader.Help(s,"/WalkSpeed <number>","WalkSpeed");
		}
		if(s instanceof Player == false) {
			Loader.Help(s,"/WalkSpeed <player> <number>","WalkSpeed");
		}
	}
	public boolean onCommand(CommandSender s, Command cmd, String label,String[] args) {

	if(args.length==0) {
		if(API.hasPerm(s, "ServerControl.WalkSpeed")) {
			speed(s);
			return true;
		}return true;}
	if(args.length==1) {
		if(s instanceof Player == false) {speed(s);return true;}else {
			if(API.hasPerm(s, "ServerControl.WalkSpeed")) {
		double flightmodifier=TheAPI.getNumbersAPI(args[0]).getDouble();
		if(flightmodifier>10.0)flightmodifier = 10.0;
		if(flightmodifier<-10.0)flightmodifier = -10.0;
		((Player) s).setWalkSpeed((float)flightmodifier/10);
		Loader.me.set("Players."+((Player) s).getName()+".WalkSpeed", flightmodifier/10);
		Configs.chatme.save();Loader.msg(Loader.s("Prefix")+Loader.s("WalkSpeed.WalkSpeed")
		.replace("%player%", s.getName())
		.replace("%playername%", ((Player) s).getDisplayName())
		.replace("%speed%", String.valueOf(flightmodifier)),s);
		return true;
	}return true;}}
	if(args.length==2) {
		if(API.hasPerm(s, "ServerControl.WalkSpeed")) {
			Player target = Bukkit.getPlayer(args[0]);
		if(target!=null) {
			double flightmodifier=TheAPI.getNumbersAPI(args[1]).getDouble();
			if(flightmodifier>10.0)flightmodifier = 10.0;
			if(flightmodifier<-10.0)flightmodifier = -10.0;
		target.setWalkSpeed((float)flightmodifier/10);
		Loader.me.set("Players."+target.getName()+".WalkSpeed", flightmodifier/10);
		Configs.chatme.save();Loader.msg(Loader.s("Prefix")+Loader.s("WalkSpeed.WalkSpeedPlayer")
				.replace("%player%", target.getName())
				.replace("%playername%", target.getDisplayName())
				.replace("%speed%", String.valueOf(flightmodifier)),target);
		Loader.msg(Loader.s("Prefix")+Loader.s("WalkSpeed.WalkSpeed")
		.replace("%player%", target.getName())
		.replace("%playername%", target.getDisplayName())
		.replace("%speed%", String.valueOf(flightmodifier)),s);
		return true;
	}
		Loader.msg(Loader.PlayerNotOnline(args[0]),s);
		return true;
		}return true;}return false;}}