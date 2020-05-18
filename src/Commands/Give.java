package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.StringUtil;

import ServerControl.API;
import ServerControl.Loader;
import Utils.Repeat;
import Utils.XMaterial;
import me.Straiker123.MultiMap;
import me.Straiker123.TheAPI;

public class Give implements CommandExecutor, TabCompleter {
	List<String> list = new ArrayList<String>();

	public Give() {
		for (Material ss : Material.values()) {
			if (TheAPI.getItemCreatorAPI(ss).isItem(false))
				list.add(ss.name());
		}
		for (String add : Arrays.asList("POTION_OF_", "SPLASH_POTION_OF_", "LINGERING_POTION_OF_")) {
			list.add(add + "MINING_FATIQUE");
			list.add(add + "GLOWING");

			list.add(add + "HASTE");
			list.add(add + "HASTE2");
			list.add(add + "HASTE3");

			list.add(add + "INVISIBILITY");
			list.add(add + "INVISIBILITY2");

			list.add(add + "NIGHT_VISION");
			list.add(add + "NIGHT_VISION2");

			list.add(add + "LEAPING");
			list.add(add + "LEAPING2");
			list.add(add + "LEAPING3");

			list.add(add + "FIRE_RESISTANCE");
			list.add(add + "FIRE_RESISTANCE2");

			list.add(add + "SPEED");
			list.add(add + "SPEED2");
			list.add(add + "SPEED3");

			list.add(add + "SLOWNESS");
			list.add(add + "SLOWNESS2");
			list.add(add + "SLOWNESS3");

			list.add(add + "WATER_BREATHING");
			list.add(add + "WATER_BREATHING2");

			list.add(add + "HEALING");
			list.add(add + "HEALING2");

			list.add(add + "HARMING");
			list.add(add + "HARMING2");

			list.add(add + "POISON");
			list.add(add + "POISON2");
			list.add(add + "POISON3");

			list.add(add + "REGENERATION");
			list.add(add + "REGENERATION2");
			list.add(add + "REGENERATION3");

			list.add(add + "STRENGHT");
			list.add(add + "STRENGHT2");
			list.add(add + "STRENGHT3");

			list.add(add + "WEAKNESS");
			list.add(add + "WEAKNESS2");

			list.add(add + "LUCK");

			list.add(add + "SWIFTNESS");
			list.add(add + "SWIFTNESS2");
			list.add(add + "SWIFTNESS3");
			if (TheAPI.isNewVersion()) {
				list.add(add + "DOLPHINS_GRACE");
				list.add(add + "CONDUIT_POWER");
				list.add(add + "BAD_OMEN");
				list.add(add + "HERO_OF_VILLAGE");
				list.add(add + "TURTLE_MASTER");
				list.add(add + "TURTLE_MASTER2");
				list.add(add + "TURTLE_MASTER3");
				list.add(add + "SLOW_FALLING");
				list.add(add + "SLOW_FALLING2");
			}
		}
	}

	private ItemStack getPotion(String s) {
		Material args1 = XMaterial.POTION.parseMaterial(); // type
		String args2 = null; // eff
		int args3 = 0; // level
		int args4 = 0; // time
		boolean multi = false;
		MultiMap<PotionEffectType> a = TheAPI.getMultiMap();
		if (s.toUpperCase().startsWith("POTION_OF_")) {
			args1 = XMaterial.POTION.parseMaterial();
			args2 = s.toUpperCase().replaceFirst("POTION_OF_", "").replaceAll("[0-9]", "");
			args3 = TheAPI.getStringUtils().getInt(s);
		}
		if (s.toUpperCase().startsWith("SPLASH_POTION_OF_")) {
			args1 = XMaterial.SPLASH_POTION.parseMaterial();
			args2 = s.toUpperCase().replaceFirst("SPLASH_POTION_OF_", "").replaceAll("[0-9]", "");
			args3 = TheAPI.getStringUtils().getInt(s);
		}
		if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
			args1 = XMaterial.LINGERING_POTION.parseMaterial();
			args2 = s.toUpperCase().replaceFirst("LINGERING_POTION_OF_", "").replaceAll("[0-9]", "");
			args3 = TheAPI.getStringUtils().getInt(s);

		}
		if (args1 == null)
			return null;
		switch (s.toUpperCase().replaceFirst("LINGERING_", "").replaceFirst("SPLASH_", "").replaceFirst("POTION_OF_",
				"")) {

		case "INVISIBILITY":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "INVISIBILITY2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;
		case "NIGHT_VISION":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "NIGHT_VISION2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;

		case "FIRE_RESISTANCE":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "FIRE_RESISTANCE2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;
		case "SWIFTNESS":
		case "SPEED":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "SWIFTNESS2":
		case "SPEED2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;
		case "SWIFTNESS3":
		case "SPEED3":
			args3 = 2;
			args4 = 90;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;

		case "WATER_BREATHING":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "WATER_BREATHING2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;

		case "HEALING":
			args3 = 1;
			args4 = 0;
			break;
		case "HEALING2":
			args3 = 2;
			args4 = 0;
			break;

		case "HARMING":
			args3 = 1;
			args4 = 0;
			break;
		case "HARMING2":
			args3 = 2;
			args4 = 0;
			break;

		case "POISON":
			args3 = 1;
			args4 = 45;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 11;
			}
			break;
		case "POISON2":
			args3 = 1;
			args4 = 90;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;
		case "POISON3":
			args3 = 2;
			args4 = 21;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 5;
			}
			break;

		case "REGENERATION":
			args3 = 1;
			args4 = 45;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 11;
			}
			break;
		case "REGENERATION2":
			args3 = 1;
			args4 = 90;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;
		case "REGENERATION3":
			args3 = 2;
			args4 = 22;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 5;
			}
			break;

		case "HASTE":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "HASTE2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;
		case "HASTE3":
			args3 = 2;
			args4 = 90;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;

		case "STRENGHT":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 45;
			}
			break;
		case "STRENGHT2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 120;
			}
			break;
		case "STRENGHT3":
			args3 = 2;
			args4 = 90;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;

		case "WEAKNESS":
			args3 = 1;
			args4 = 180;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;
		case "WEAKNESS2":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 60;
			}
			break;
		case "LUCK":
			args3 = 1;
			args4 = 480;
			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 75;
			}
			break;
		case "TURTLE_MASTER":
			multi = true;
			a.put(PotionEffectType.SLOW, 4, 20);
			a.put(PotionEffectType.DAMAGE_RESISTANCE, 3, 20);
			break;
		case "TURTLE_MASTER2":
			multi = true;
			a.put(PotionEffectType.SLOW, 4, 40);
			a.put(PotionEffectType.DAMAGE_RESISTANCE, 3, 40);
			break;
		case "TURTLE_MASTER3":
			multi = true;
			a.put(PotionEffectType.SLOW, 4, 20);
			a.put(PotionEffectType.DAMAGE_RESISTANCE, 4, 20);
			break;

		case "CONDUIT_POWER":
		case "DOLPHINS_GRACE":
		case "BAD_OMEN":
		case "HERO_OF_VILLAGE":
		case "GLOWING":
		case "SLOW_FALLING":
		case "MINING_FATIQUE":
			args3 = 1;
			args4 = 90;

			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 22;
			}
			break;
		case "SLOW_FALLING2":
			args3 = 1;
			args4 = 240;

			if (s.toUpperCase().startsWith("LINGERING_POTION_OF_")) {
				args4 = 60;
			}
			break;

		}
		args2 = args2.replace("STRENGHT", "INCREASE_DAMAGE").replace("HARMING", "HARM").replace("HEALING", "HEAL")
				.replace("SWIFTNESS", "SPEED");

		ItemStack h = new ItemStack(args1);
		PotionMeta g = (PotionMeta) h.getItemMeta();
		if (multi) {
			for (PotionEffectType f : a.getKeySet()) { // effect
				Object[] o = a.getValues(f).toArray();// values
				int i = TheAPI.getStringUtils().getInt(o[0].toString());
				int ib = TheAPI.getStringUtils().getInt(o[1].toString());
				g.addCustomEffect(new PotionEffect(f, i * 20, (ib == 0 ? 1 : ib)), true);
			}
		} else
			g.addCustomEffect(new PotionEffect(PotionEffectType.getByName(args2), args4 * 20, (args3 == 0 ? 1 : args3)),
					true);

		if (s.toUpperCase().startsWith("LINGERING_POTION_OF_"))
			g.addCustomEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 20, 1), true);
		h.setItemMeta(g);
		return h;
	}

	public String getItem(String s) {
		if (list.contains(s.toUpperCase()))
			return s.toUpperCase();
		return null;
	}

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if (API.hasPerm(s, "ServerControl.Give")) {
			if (args.length == 0) {
				Loader.Help(s, "/Give <player> <item> <amount>", "Give");
				return true;
			}
			if (args.length == 1) {
				Player ps = TheAPI.getPlayer(args[0]);
				if (ps == null) {
					String g = args[0].toLowerCase().replaceFirst("minecraft:", "").toUpperCase();

					if (s instanceof Player == false) {
						Loader.Help(s, "/Give <player> <item> <amount>", "Give");
						return true;
					}
					if (getItem(g) != null) {
						if (API.hasPerm(s, "ServerControl." + getItem(g)) || API.hasPerm(s, "ServerControl.Give.*")) {
							Player p = (Player) s;
							try {
								if (!g.startsWith("LINGERING_POTION_OF_") && !g.startsWith("SPLASH_POTION_OF_")
										&& !g.startsWith("POTION_OF_"))
									TheAPI.giveItem(p, XMaterial.matchXMaterial(g).get().parseMaterial(), 1);
								else
									TheAPI.giveItem(p, getPotion(g));
								Loader.msg(Loader.s("Prefix") + API.replacePlayerName(Loader.s("Give.Given"), p)
										.replace("%amount%", "1").replace("%item%", getItem(g)), s);
								return true;
							} catch (Exception e) {
								Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", g), s);
								return true;
							}
						}
						return true;
					}
					Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", args[0]), s);
					return true;
				}
				Loader.Help(s, "/Give <player> <item> <amount>", "Give");
				return true;
			}
			if (args.length == 2) {
				Player ps = TheAPI.getPlayer(args[0]);
				if (ps == null) {
					String g = args[0].toLowerCase().replaceFirst("minecraft:", "").toUpperCase();
					if (args[0].equals("*")) {
						Repeat.a(s, "give * " + args[1].toLowerCase().replaceFirst("minecraft:", "").toUpperCase());
						return true;
					}
					if (getItem(g) != null) {
						ps = (Player) s;
						try {
							if (!g.startsWith("LINGERING_POTION_OF_") && !g.startsWith("SPLASH_POTION_OF_")
									&& !g.startsWith("POTION_OF_"))
								TheAPI.giveItem(ps, XMaterial.matchXMaterial(g).get().parseMaterial(), 1);
							else {
								ItemStack a = getPotion(g);
								a.setAmount(TheAPI.getStringUtils().getInt(args[1]));
								TheAPI.giveItem(ps, a);
							}
							Loader.msg(Loader.s("Prefix") + API.replacePlayerName(Loader.s("Give.Given"), ps)
									.replace("%amount%", TheAPI.getStringUtils().getInt(args[1]) + "")
									.replace("%item%", getItem(g)), s);
							return true;

						} catch (Exception e) {
							Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", g), s);
							return true;
						}
					}
					if (s instanceof Player == false)
						Loader.msg(Loader.PlayerNotOnline(args[1]), s);
					else
						Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", g), s);
					return true;

				}
				String g = args[1].toLowerCase().replaceFirst("minecraft:", "").toUpperCase();
				if (getItem(g) != null) {
					if (!g.startsWith("LINGERING_POTION_OF_") && !g.startsWith("SPLASH_POTION_OF_")
							&& !g.startsWith("POTION_OF_"))
						TheAPI.giveItem(ps, XMaterial.matchXMaterial(g).get().parseMaterial(), 1);
					else
						TheAPI.giveItem(ps, getPotion(g));
					Loader.msg(Loader.s("Prefix") + API.replacePlayerName(Loader.s("Give.Given"), ps)
							.replace("%item%", getItem(g)).replace("%amount%", "1"), s);
					return true;
				}
				Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", g), s);
				return true;
			}
			if (args.length == 3) {
				Player ps = TheAPI.getPlayer(args[0]);
				if (ps != null) {
					String g = args[1].toLowerCase().replaceFirst("minecraft:", "").toUpperCase();
					if (args[0].equals("*")) {
						Repeat.a(s, "give * " + g + " " + args[2]);
						return true;
					}
					if (getItem(args[1]) != null) {
						if (!g.startsWith("LINGERING_POTION_OF_") && !g.startsWith("SPLASH_POTION_OF_")
								&& !g.startsWith("POTION_OF_"))
							TheAPI.giveItem(ps, XMaterial.matchXMaterial(g).get().parseMaterial(),
									TheAPI.getStringUtils().getInt(args[2]));
						else {
							ItemStack a = getPotion(g);
							a.setAmount(TheAPI.getStringUtils().getInt(args[2]));
							TheAPI.giveItem(ps, a);
						}
						Loader.msg(Loader.s("Prefix")
								+ API.replacePlayerName(Loader.s("Give.Given"), ps).replace("%item%", getItem(g))
										.replace("%amount%", TheAPI.getStringUtils().getInt(args[2]) + ""),
								s);
						return true;
					}
					Loader.msg(Loader.s("Prefix") + Loader.s("Give.UknownItem").replace("%item%", g), s);
					return true;
				}
				if (args[0].equals("*")) {
					Repeat.a(s, "give * " + args[1].toLowerCase().replaceFirst("minecraft:", "").toUpperCase() + " "
							+ TheAPI.getStringUtils().getInt(args[2]));
					return true;
				}
				Loader.msg(Loader.PlayerNotOnline(args[1]), s);
				return true;
			}

		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args) {
		List<String> c = new ArrayList<>();
		if (s.hasPermission("ServerControl.Give")) {
			List<String> pls = new ArrayList<String>();
			for (Player p : TheAPI.getOnlinePlayers())
				pls.add(p.getName());
			if (args.length == 1)
				c.addAll(StringUtil.copyPartialMatches(args[0], pls, new ArrayList<>()));
			if (args.length == 2) {
				c.addAll(StringUtil.copyPartialMatches(args[1], list, new ArrayList<>()));
			}
			if (args.length == 3) {
				c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("1", "3", "5", "10", "15"),
						new ArrayList<>()));
			}
		}
		return c;
	}
}
