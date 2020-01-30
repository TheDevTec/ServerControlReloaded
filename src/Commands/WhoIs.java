package Commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ServerControl.API;
import ServerControl.Loader;
import ServerControl.API.SeenType;

public class WhoIs implements CommandExecutor, TabCompleter {

    public static String getCountry(String a) {
    	try {
    		InetSocketAddress ip = new InetSocketAddress(a, 0);
        URL url = new URL("http://ip-api.com/json/" + ip.getHostName());
        BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder entirePage = new StringBuilder();
        String inputLine;
        while ((inputLine = stream.readLine()) != null)
            entirePage.append(inputLine);
        stream.close();
        if(!(entirePage.toString().contains("\"country\":\"")))
            return null;
        return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
    	}catch(Exception e) {
    		return "&7Uknown.";
    	}
    }
    
    private String getName(String s) {
    	if(Bukkit.getPlayer(s)!=null)return Bukkit.getPlayer(s).getDisplayName();
    	return s;
    }
	
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] a) {
		if(API.hasPerm(s, "ServerControl.WhoIs")) {
		if(a.length==0){
			Loader.Help(s, "/WhoIs <player>", "WhoIs");
			return true;
		}
		String d = Loader.me.getString("Players."+a[0]);
		if(d==null) {
			Loader.msg(Loader.PlayerNotEx(a[0]),s);
			return true;
		}
		String ip = API.getIPAdress(a[0]);
		if(ip==null)ip="&7Uknown";
		String what = "Offline";
		String afk = "No";
		String seen = API.getSeen(a[0], SeenType.Offline);
		if(Bukkit.getPlayer(a[0])!=null && Bukkit.getPlayer(a[0]).getName().equals(a[0])) {
			what="Online";
			seen = API.getSeen(a[0], SeenType.Online);
			if(API.isAFK(Bukkit.getPlayer(a[0])))afk="Yes";
		}
		String fly = "Disabled";
		String god = "Disabled";
		if(Loader.me.getBoolean("Players."+a[0]+".Fly"))fly="Enabled";
		if(Loader.me.getBoolean("Players."+a[0]+".God"))god="Enabled";
		String op = "No";
		for(OfflinePlayer w : Bukkit.getOperators()) {
			if(w.getName().equals(a[0]))op="Yes";
		}
		
		Loader.msg("&8----- &a"+getName(a[0])+" &8-----",s);
		Loader.msg("&6Nickname: &a"+a[0],s);
		Loader.msg("&6"+what+": &a"+seen,s);
		Loader.msg("&6Fly: &a"+fly,s);
		Loader.msg("&6God: &a"+god,s);
		Loader.msg("&6AFK: &a"+afk,s);
		Loader.msg("&6OP: &a"+op,s);
		Loader.msg("&6IP: &a"+ip.replaceFirst("/", ""),s);
		Loader.msg("&6Country: &a"+getCountry(ip),s);
		Loader.msg("&8---------------",s);
		return true;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		if(args.length==1)
		return null;
		return new ArrayList<>();
	}

}