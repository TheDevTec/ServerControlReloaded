package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import ServerControl.API;
import ServerControl.Loader;
import Utils.Configs;
import Utils.TabList;

public class Tab implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String awd, String[] args) {
		if(args.length==0) {
			Loader.msg(Loader.s("Prefix")+"Tablist created by Straiker123, Discord: https://discord.gg/cZax8d4",s);
		if(s.hasPermission("ServerCotrol.TAB")) {
			Loader.Help(s, "/TAB Prefix <group> <value>", "TabList.Prefix");
			Loader.Help(s, "/TAB Suffix <group> <value>", "TabList.Suffix");
			Loader.Help(s, "/TAB Priorite <group> <value>", "TabList.Priorite");
			Loader.Help(s, "/TAB Create <group>", "TabList.Create");
			Loader.Help(s, "/TAB Delete <group>", "TabList.Delete");
			Loader.Help(s, "/TAB Reload", "TabList.Reload");
		}
		return true;
		}
		if(API.hasPerm(s,"ServerCotrol.TAB")) {
			if(args[0].equalsIgnoreCase("Reload")) {
				Loader.msg(Loader.s("Prefix")+"&e----------------- &bTab Reload &e-----------------",s);
			    Loader.msg("",s);
				TabList.removeTab();
				Configs.TabLoading();
				TabList.setFooterHeader();
				TabList.setNameTag();
				Loader.msg(Loader.s("Prefix")+Loader.s("ConfigReloaded"),s);
				return true;
			}
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length==1) {
					Loader.Help(s, "/TAB Create <group>", "TabList.Create");
					return true;
				}
				if(Loader.tab.getString("Groups."+args[1])!=null) {
					Loader.msg(Loader.s("Prefix")+Loader.s("TabList.AlreadyExist").replace("%group%", args[1]),s);
					return true;
				}
				Loader.msg(Loader.s("Prefix")+Loader.s("TabList.GroupCreated").replace("%group%", args[1]),s);
				Loader.tab.set("Groups."+args[1], "");
        		Configs.tab.save();
				return true;
			}
			if(args[0].equalsIgnoreCase("delete")) {
				if(args.length==1) {
					Loader.Help(s, "/TAB Delete <group>", "TabList.Delete");
					return true;
				}
				if(Loader.tab.getString("Groups."+args[1])==null) {
					Loader.msg(Loader.s("Prefix")+Loader.s("TabList.DoNotExist").replace("%group%", args[1]),s);
					return true;
				}
				Loader.msg(Loader.s("Prefix")+Loader.s("TabList.GroupDeleted").replace("%group%", args[1]),s);
				Loader.tab.set("Groups."+args[1], null);
        		Configs.tab.save();
				return true;
			}
			if(args.length==1||args.length==2)
			if(args[0].equalsIgnoreCase("prefix")||args[0].equalsIgnoreCase("suffix")||args[0].equalsIgnoreCase("priorite")) {
				Loader.Help(s, "/TAB Prefix <group> <value>", "TabList.Prefix");
				Loader.Help(s, "/TAB Suffix <group> <value>", "TabList.Suffix");
				Loader.Help(s, "/TAB Priorite <group> <value>", "TabList.Priorite");
				Loader.Help(s, "/TAB Create <group>", "TabList.Create");
				Loader.Help(s, "/TAB Delete <group>", "TabList.Delete");
				Loader.Help(s, "/TAB Reload", "TabList.Reload");
				return true;
			}
			if(args[0].equalsIgnoreCase("priorite")) {
				Loader.msg(Loader.s("Prefix")+Loader.s("TabList.PrioriteSet").replace("%priorite%", args[2]).replace("%group%", args[1]),s);
			Loader.tab.set("Groups."+args[1]+".Priorite", args[2]);
    		Configs.tab.save();
			return true;
			}
			if(args[0].equalsIgnoreCase("prefix")) {
				String msg = "";
					for (int i = 2; i < args.length; i++) {
						msg = msg + args[i]+" ";
					}
					msg=msg.substring(0, msg.length()-1);
					Loader.msg(Loader.s("Prefix")+
							Loader.s("TabList.PrefixSet").replace("%prefix%", msg).replace("%group%", args[1]),s);
			Loader.tab.set("Groups."+args[1]+".Prefix", msg);
    		Configs.tab.save();
			return true;
		}
			if(args[0].equalsIgnoreCase("suffix")) {
				String msg = "";
					for (int i = 2; i < args.length; i++) {
						msg = msg + args[i]+" ";
					}
					msg=msg.substring(0, msg.length()-1);
					Loader.msg(Loader.s("Prefix")+
							Loader.s("TabList.SuffixSet").replace("%suffix%", msg).replace("%group%", args[1]),s);
			Loader.tab.set("Groups."+args[1]+".Suffix", msg);
    		Configs.tab.save();
			return true;
		}
			if(!args[0].equalsIgnoreCase("suffix")&&!args[0].equalsIgnoreCase("prefix")&&!args[0].equalsIgnoreCase("priorite")&&!args[0].equalsIgnoreCase("reload")&&!args[0].equalsIgnoreCase("create")&&!args[0].equalsIgnoreCase("delete")) {
				Loader.Help(s, "/TAB Prefix <group> <value>", "TabList.Prefix");
				Loader.Help(s, "/TAB Suffix <group> <value>", "TabList.Suffix");
				Loader.Help(s, "/TAB Priorite <group> <value>", "TabList.Priorite");
				Loader.Help(s, "/TAB Create <group>", "TabList.Create");
				Loader.Help(s, "/TAB Delete <group>", "TabList.Delete");
				Loader.Help(s, "/TAB Reload", "TabList.Reload");
			
			return true;
			}
			}
			return true;
	}
	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		if(s.hasPermission("ServerControl.TAB")) {
			if(args.length==1) {
        		c.addAll(StringUtil.copyPartialMatches(args[0],Arrays.asList("Prefix", "Suffix", "Priorite", "Reload", "Create", "Delete"), new ArrayList<>()));
			}
			if(args.length==2) {
				if(args[0].equalsIgnoreCase("Prefix")||args[0].equalsIgnoreCase("Suffix")||args[0].equalsIgnoreCase("Priorite")||args[0].equalsIgnoreCase("Delete"))
        		c.addAll(StringUtil.copyPartialMatches(args[1], Loader.tab.getConfigurationSection("Groups").getKeys(false), new ArrayList<>()));
				if(args[0].equalsIgnoreCase("Create")) {
					
					for(String a:Loader.tab.getConfigurationSection("Groups").getKeys(false))
					if(args[1].equals(a))
	        		c.addAll(StringUtil.copyPartialMatches(args[1], Arrays.asList("AlreadyExists"), new ArrayList<>()));
					else {
						List<String> list = new ArrayList<String>();
						if(Loader.vault!=null && API.existVaultPlugin()) {
							for(String d:Loader.vault.getGroups()) {
								list.add(d);
							}
						}else
							list=Arrays.asList("?");
		        		c.addAll(StringUtil.copyPartialMatches(args[1], list, new ArrayList<>()));
				}}
			}
		}
		return c;
	}
}