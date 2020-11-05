package me.DevTec.ServerControlReloaded.Commands.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.DevTec.ServerControlReloaded.SCR.Loader;
import me.DevTec.ServerControlReloaded.SCR.Loader.Placeholder;
import me.DevTec.ServerControlReloaded.Utils.Repeat;
import me.DevTec.TheAPI.TheAPI;
import me.DevTec.TheAPI.Utils.StringUtils;

public class Exp implements CommandExecutor, TabCompleter {
	public Exp() {
		aliases.put("give", Arrays.asList("give", "add"));
		aliases.put("take", Arrays.asList("take", "del", "delete", "remove"));
		aliases.put("balance", Arrays.asList("bal", "balance"));
		aliases.put("set", Arrays.asList("set"));
	}

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Loader.Help(s, "Experiences", "Other");
			return true;
		}
		if (isAlias(args[0], "set")) {
			if (Loader.has(s, "Experiences", "Other", "Set")) {
				if (args.length == 1 || args.length == 2) {
					Loader.Help(s, "Experiences", "Other");
					return true;
				}
				Player p = TheAPI.getPlayer(args[1]);
				if(args.length==3) {
					if (p == null) {
						if (args[0].equals("*")) {
							Repeat.a(s, "xp set * " + StringUtils.getFloat(args[2]));
							return true;
						}
						Loader.notOnline(s, args[1]);
						return true;
					}
					float add = StringUtils.getFloat(args[2]);
					p.setExp(add>0?add:0);
					Loader.sendMessages(s, "Experiences.Set", Placeholder.c().replace("%player%", p.getName())
							.replace("%playername%", p.getDisplayName())
							.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
					return true;
					}
					if (p == null) {
						if (args[0].equals("*")) {
							Repeat.a(s, "xp set * " + StringUtils.getFloat(args[2])+" "+args[3]);
							return true;
						}
						Loader.notOnline(s, args[1]);
						return true;
					}
					if(args[3].toLowerCase().contains("level")) {
						int add = StringUtils.getInt(args[2]);
						p.setLevel(add>0?add:0);
						Loader.sendMessages(s, "Experiences.Set", Placeholder.c().replace("%player%", p.getName())
								.replace("%playername%", p.getDisplayName())
								.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.level").toString()));
						
					}else {
						float add = StringUtils.getFloat(args[2]);
						p.setExp(add>0?add:0);
						Loader.sendMessages(s, "Experiences.Set", Placeholder.c().replace("%player%", p.getName())
								.replace("%playername%", p.getDisplayName())
								.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
					}
					return true;
			}
			Loader.noPerms(s, "Experiences", "Other", "Set");
			return true;
		}
		if (isAlias(args[0], "balance")) {
			if (Loader.has(s, "Experiences", "Other", "Balance")) {
				if (args.length == 1) {
					Loader.Help(s, "Experiences", "Other");
					return true;
				}
				Player p = TheAPI.getPlayer(args[1]);
				if (p == null) {
					Loader.notOnline(s, args[1]);
					return true;
				}
				Loader.sendMessages(s, "Experiences.Balance", Placeholder.c().replace("%player%", p.getName())
						.replace("%playername%", p.getDisplayName())
						.replace("%amount%", "" + ((args.length>=3?args[2].toLowerCase().contains("level"):false)?p.getLevel(): p.getExp())).replace("%type%", Loader.getTranslation("Experiences.Words."+(((args.length>=3?args[2].toLowerCase().contains("level"):false)?"Level": "Exp"))).toString()));
				return true;
			}
			Loader.noPerms(s, "Experiences", "Other", "Balance");
			return true;
		}
		if (isAlias(args[0], "give")) {
			if (Loader.has(s, "Experiences", "Other", "Add")) {
				if (args.length == 1 || args.length == 2) {
					Loader.Help(s, "Experiences", "Other");
					return true;
				}
				Player p = TheAPI.getPlayer(args[1]);
				if(args.length==3) {
				if (p == null) {
					if (args[0].equals("*")) {
						Repeat.a(s, "xp add * " + StringUtils.getFloat(args[2]));
						return true;
					}
					Loader.notOnline(s, args[1]);
					return true;
				}
				float add = p.getExp()+StringUtils.getFloat(args[2]);
				p.setExp(add>0?add:0);
				Loader.sendMessages(s, "Experiences.Given", Placeholder.c().replace("%player%", p.getName())
						.replace("%playername%", p.getDisplayName())
						.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
				return true;
				}
				if (p == null) {
					if (args[0].equals("*")) {
						Repeat.a(s, "xp add * " + StringUtils.getFloat(args[2])+" "+args[3]);
						return true;
					}
					Loader.sendMessages(s, "Missing.Player.Offline", Placeholder.c().add("%player%", args[1]).add("%playername%", args[1]));
					return true;
				}
				if(args[3].toLowerCase().contains("level")) {
					int add = p.getLevel()+StringUtils.getInt(args[2]);
					p.setLevel(add>0?add:0);
					Loader.sendMessages(s, "Experiences.Given", Placeholder.c().replace("%player%", p.getName())
							.replace("%playername%", p.getDisplayName())
							.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Level").toString()));
					}else {
					float add = p.getExp()+StringUtils.getFloat(args[2]);
					p.setExp(add>0?add:0);
					Loader.sendMessages(s, "Experiences.Given", Placeholder.c().replace("%player%", p.getName())
							.replace("%playername%", p.getDisplayName())
							.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
					}
				return true;
		}
		Loader.noPerms(s, "Experiences", "Other", "Give");
		return true;
		}
		if (isAlias(args[0], "take")) {
			if (Loader.has(s, "Experiences", "Other", "Remove")) {
				if (args.length == 1 || args.length == 2) {
					Loader.Help(s, "Experiences", "Other");
					return true;
				}
				Player p = TheAPI.getPlayer(args[1]);
				if(args.length==3) {
				if (p == null) {
					if (args[0].equals("*")) {
						Repeat.a(s, "xp remove * " + StringUtils.getFloat(args[2]));
						return true;
					}
					Loader.notOnline(s, args[1]);
					return true;
				}
				float take = p.getExp()-StringUtils.getFloat(args[2]);
				p.setExp(take<0?0:take);
				Loader.sendMessages(s, "Experiences.Taken", Placeholder.c().replace("%player%", p.getName())
						.replace("%playername%", p.getDisplayName())
						.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
				return true;
				}
				if (p == null) {
					if (args[0].equals("*")) {
						Repeat.a(s, "xp remove * " + StringUtils.getFloat(args[2])+" "+args[3]);
						return true;
					}
					Loader.notOnline(s, args[1]);
					return true;
				}
				if(args[3].toLowerCase().contains("level")) {
					int take = p.getLevel()-StringUtils.getInt(args[2]);
					p.setLevel(take<0?0:take);
					Loader.sendMessages(s, "Experiences.Taken", Placeholder.c().replace("%player%", p.getName())
							.replace("%playername%", p.getDisplayName())
							.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Level").toString()));
					}else {
					float take = p.getExp()-StringUtils.getFloat(args[2]);
					p.setExp(take<0?0:take);
					Loader.sendMessages(s, "Experiences.Taken", Placeholder.c().replace("%player%", p.getName())
							.replace("%playername%", p.getDisplayName())
							.replace("%amount%", "" + StringUtils.getFloat(args[2])).replace("%type%", Loader.getTranslation("Experiences.Words.Exp").toString()));
					}
				return true;
			}
			Loader.noPerms(s, "Experiences", "Other", "Remove");
			return true;
		}
		Loader.Help(s, "Experiences", "Other");
		return true;
	}

	HashMap<String, List<String>> aliases = new HashMap<String, List<String>>();

	boolean isAlias(String a, String b) {
		boolean bol = false;
		for (String s : aliases.get(b))
			if (s.equalsIgnoreCase(a)) {
				bol = true;
				break;
			}
		return bol;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		if (args.length == 1) {
			if (Loader.has(s, "Experiences", "Other", "Balance"))
				c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Balance"), new ArrayList<>()));
			if (Loader.has(s, "Experiences", "Other", "Give"))
				c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Give"), new ArrayList<>()));
			if (Loader.has(s, "Experiences", "Other", "Remove"))
				c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Take"), new ArrayList<>()));
			if (Loader.has(s, "Experiences", "Other", "Set"))
				c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Set"), new ArrayList<>()));
		}
		if (isAlias(args[0], "balance") && Loader.has(s, "Experiences", "Other", "Balance")
				|| isAlias(args[0], "take") && Loader.has(s, "Experiences", "Other", "Remove")
				|| isAlias(args[0], "give") && Loader.has(s, "Experiences", "Other", "Give")
				|| isAlias(args[0], "set") && Loader.has(s, "Experiences", "Other", "Set")) {
			if (args.length == 2) {
				return null;
			}
			if (args.length == 3) {
				if (isAlias(args[0], "take") && Loader.has(s, "Experiences", "Other", "Remove")
						|| isAlias(args[0], "give") && Loader.has(s, "Experiences", "Other", "Give")
						|| isAlias(args[0], "set") && Loader.has(s, "Experiences", "Other", "Set"))
					c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("?"), new ArrayList<>()));
			}
		}
		return c;
	}

}