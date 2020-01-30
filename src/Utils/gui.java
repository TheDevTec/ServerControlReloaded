package Utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ServerControl.Loader;
import me.Straiker123.GUICreatorAPI;
import me.Straiker123.GUICreatorAPI.Options;
import me.Straiker123.ItemCreatorAPI;
import me.Straiker123.TheAPI;

public class gui {
	public static enum Type{
		n,
		Chat,
		Cmd
	}

	public static void openGUI(Player p, Type type) {
		String t = null;
		switch(type) {
		case Cmd:
			t=" &6Commands";
			break;
		case Chat:
			t=" &6Chat";
			break;
		case n:
			break;
		}
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(p);
		String w = t;
		if(w==null)w="";
		a.setTitle("&4Server Control Reloaded &cManager"+w);
		a.setSize(9);
		if(t==null) {
			HashMap<Options, Object> s = new HashMap<Options, Object>();
			s.put(Options.CANT_PUT_ITEM, true);
			s.put(Options.CANT_BE_TAKEN, true);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					p.getOpenInventory().close();
				}});
			a.setItem(0, item("&cClose",XMaterial.BARRIER),s);
			s.remove(Options.RUNNABLE);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openGUI(p,Type.Chat);
				}});
			a.setItem(3, item("&6Chat",XMaterial.PAPER),s);
			s.remove(Options.RUNNABLE);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openGUI(p,Type.Cmd);
				}});
			a.setItem(5, item("&6Commands",XMaterial.COMMAND_BLOCK),s);
		}else {
			 String path = "Chat.";
			if(type==Type.Cmd)path="Commands.";

			String pat = path;
			XMaterial spam = XMaterial.LIME_DYE;
			if(!Loader.config.getBoolean(path+"AntiSpam"))
				spam=XMaterial.RED_DYE;
			HashMap<Options, Object> s = new HashMap<Options, Object>();
			s.put(Options.CANT_PUT_ITEM, true);
			s.put(Options.CANT_BE_TAKEN, true);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					Loader.config.set(pat+".AntiSpam", !Loader.config.getBoolean(pat+".AntiSpam"));
					Configs.config.save();
					openGUI(p, type);
				}});
			a.setItem(3, item("&cAntiSpam",spam),s);

				XMaterial spams = XMaterial.LIME_DYE;
				if(!Loader.config.getBoolean(path+"Caps"))
					spams=XMaterial.RED_DYE;
				
				s.remove(Options.RUNNABLE);
				s.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						Loader.config.set(pat+".Caps", !Loader.config.getBoolean(pat+".Caps"));
						Configs.config.save();
						openGUI(p, type);
					}});
				a.setItem(4, item("&6Caps",spams),s);
			
			
				XMaterial sw = XMaterial.LIME_DYE;
			if(!Loader.config.getBoolean(path+"AntiSwear"))
				sw=XMaterial.RED_DYE;
			s.remove(Options.RUNNABLE);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					Loader.config.set(pat+".AntiSwear", !Loader.config.getBoolean(pat+".AntiSwear"));
					Configs.config.save();
					openGUI(p, type);
				}});
			a.setItem(5, item("&cAntiSwear",sw),s);
			s.remove(Options.RUNNABLE);
			s.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openGUI(p,Type.n);
				}});
			a.setItem(0, item("&cBack",XMaterial.ARROW),s);
		}
		a.open();
	}
	public static ItemStack item(String name, XMaterial typ) {
		ItemCreatorAPI a = TheAPI.getItemCreatorAPI(typ.parseMaterial());
		a.setDisplayName(name);
		return a.create();
	}
}