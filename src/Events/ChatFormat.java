package Events;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import ServerControl.API;
import ServerControl.Loader;
import Utils.Colors;
import Utils.Configs;
import Utils.MultiWorldsGUI;
import Utils.setting;
import me.Straiker123.TheAPI;

@SuppressWarnings("deprecation")
public class ChatFormat implements Listener {
	static Loader plugin = Loader.getInstance;
	String m;

	public static int capitalCount(String string){
		char ch = (char)string.charAt(0);
		int upperCaseCount = 0;
		for(int i = 0; i < string.length(); i++)
		{
			ch = (char)string.charAt(i);
	        if (Character.isAlphabetic(ch) && Character.isUpperCase(ch))
	        {
	        	upperCaseCount++;
	        }
		}
		return upperCaseCount;
	}
	
	public static String r(Player p, String s, String msg, boolean a) {
		
		String group = Loader.FormatgetGroup(p);
		if(Loader.vault!=null)
			group=Loader.vault.getPrimaryGroup(p);
		String name = p.getName();
		String displayname = name;
		if(a) {
			displayname=p.getDisplayName();
			name=displayname;
		}else {
			if(Loader.me.getString("Players."+p.getName()+".DisplayName") != null) {
			name=Loader.me.getString("Players."+p.getName()+".DisplayName");
			displayname=Loader.me.getString("Players."+p.getName()+".DisplayName");
			}
		}
		String customname = p.getName();
		if(p.getCustomName()!=null)customname=p.getCustomName();
			s= s.replace("%%player%%",name)
				.replace("%%group%%", Loader.FormatgetGroup(p))
				.replace("%%time%%", new SimpleDateFormat("HH:mm:ss").format(new Date()))
				.replace("%%x%%", String.valueOf(p.getLocation().getBlockX()))
				.replace("%%y%%", String.valueOf(p.getLocation().getBlockY()))
				.replace("%%z%%", String.valueOf(p.getLocation().getBlockZ()))
				.replace("%%vault-group%%", group)
				.replace("%%vault-prefix%%", Loader.getInstance.getPrefix(p))
				.replace("%%prefix%%", Loader.getInstance.getPrefix(p))
				.replace("%%vault-suffix%%", Loader.getInstance.getSuffix(p))
				.replace("%%suffix%%", Loader.getInstance.getSuffix(p))
				.replace("%%world%%",p.getWorld().getName())
				.replace("%%hp%%",String.valueOf(p.getHealth()))
				.replace("%%customname%%",customname)
				.replace("%%playercustomname%%",customname)
				.replace("%%playername%%",displayname)
				.replace("%%health%%",String.valueOf(p.getHealth()))
				.replace("%%food%%",String.valueOf(p.getFoodLevel()))
				.replace("%%foodlevel%%",String.valueOf(p.getFoodLevel()))
				.replace("%%level%%",String.valueOf(p.getLevel()));
		if(msg!=null)
			s=s.replace("%%message%%",msg);
		return s;
	}
	
	public static String r(String msg, CommandSender p, boolean chat) {
		if(!p.hasPermission("ServerControl.Caps")) {
			int ALLOWED_CAPITALS_PERCENT = 50;
	 		if(msg.length() > 5) {
	 		double upperCaseCount = capitalCount(msg);			
	 		if((upperCaseCount / msg.length()) * 100 > ALLOWED_CAPITALS_PERCENT){
	 			String caps = Loader.s("Security.TryingSendCaps");
	 				if(p!=null)
	 					TheAPI.broadcast(Loader.s("Prefix")+API.replacePlayerName(caps,(Player)p).replace("%message%", msg), "ServerControl.Caps");
	 				if(setting.color_chat
	 						&&
	 						setting.color_chat_perm
	 						&&
	 						p.hasPermission(Loader.config.getString("Colored-Chat.Permission"))
	 						||
	 						setting.color_chat
	 						&&
	 						!setting.color_chat_perm)
	 				return TheAPI.colorize(msg.toLowerCase());
	 				else
	 				return msg.toLowerCase();
	 				
	 		}
			if(setting.color_chat
						&&
 						setting.color_chat_perm
						&&
						p.hasPermission(Loader.config.getString("Colored-Chat.Permission"))
						||
						setting.color_chat
						&&
 						!setting.color_chat_perm)
				return TheAPI.colorize(msg);
				else
				return msg;
	 		}}
		if(setting.color_chat
					&&
						setting.color_chat_perm
					&&
					p.hasPermission(Loader.config.getString("Colored-Chat.Permission"))
					||
					setting.color_chat
					&&!setting.color_chat_perm)
			return TheAPI.colorize(msg);
		else
		return msg;
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void set(PlayerChatEvent e) {
		Player p =e.getPlayer();
	if(TheAPI.getCooldownAPI("world-create").getStart(p.getName())!=-1) {
		e.setCancelled(true);
		if(e.getMessage().toLowerCase().equals("cancel")) {
			TheAPI.getCooldownAPI("world-create").removeCooldown(p.getName());
			Loader.me.set("Players."+p.getName()+".MultiWorlds-Create",null);
			Loader.me.set("Players."+p.getName()+".MultiWorlds-Generator",null);
			Configs.chatme.save();
			TheAPI.getPlayerAPI(p).sendTitle("", "&6Cancelled");
			return;
		}
		if(TheAPI.getCooldownAPI("world-create").expired(p.getName())) {
			TheAPI.getCooldownAPI("world-create").removeCooldown(p.getName());
			MultiWorldsGUI.openInvCreate(p);
		}
		else {
			TheAPI.getCooldownAPI("world-create").removeCooldown(p.getName());
			Loader.me.set("Players."+p.getName()+".MultiWorlds-Create",Colors.remove(e.getMessage()));
			Configs.chatme.save();
			MultiWorldsGUI.openInvCreate(p);
		}
	}

	String msg = r(e.getMessage(),p,true);
	msg=msg.replace("%", "%%");
	e.setMessage(msg);

			Loader.setupChatFormat(p);
			
		if(Loader.config.getBoolean("ChatLock")) {
			if(!p.hasPermission("ServerControl.ChatLock"))  {
				e.setCancelled(true);
				Loader.msg(Loader.s("Prefix")+Loader.s("ChatLock.ChatIsLockedErrorPlayerMessage"), p);
				TheAPI.broadcast(Loader.s("Prefix")+Loader.s("ChatLock.BroadCastMessageChatLock").replace("%player%", p.getName()).replace("%playername%", p.getDisplayName()).replace("%message%", e.getMessage()), "ServerControl.ChatLock.Notify");
			}}
		if(Loader.config.getBoolean("Chat-Groups-Enabled")==true) {
		if(Loader.config.getString("Chat-Groups."+Loader.FormatgetGroup(p)+".Chat")!=null) {
		m = Loader.config.getString("Chat-Groups."+Loader.FormatgetGroup(p)+".Chat").replace("%", "%%");
	
			String format = TheAPI.getPlaceholderAPI().setPlaceholders(p,Loader.config.getString("Chat-Groups."+Loader.FormatgetGroup(p)+".Chat"));
			if(format!=null) {
			format=format.replace("%", "%%");
			format=TheAPI.colorize(format);
			format=r(p,format,e.getMessage(), true);

			e.setFormat(format);
				}}}
		
	}}