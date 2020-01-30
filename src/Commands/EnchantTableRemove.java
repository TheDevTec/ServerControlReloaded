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
import org.bukkit.util.StringUtil;

import ServerControl.API;
import ServerControl.Loader;
import me.Straiker123.TheAPI;

public class EnchantTableRemove implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(API.hasPerm(s, "ServerControl.Enchant")) {
			if(s instanceof Player) {
				if(args.length==0) {
				Loader.Help(s, "/EnchantRemove <enchant>", "EnchantRemove");
				return true;
				}
				if(args.length==1) {
					Player p = (Player)s;
						Material a = p.getItemInHand().getType();
					if(a!=Material.AIR) {
						if(p.getItemInHand().getEnchantments().containsKey(TheAPI.getEnchantmentAPI().getByName(args[0]))) {
					e(p.getItemInHand(),args[0],s);
					return true;
					}
						Loader.msg(Loader.s("Prefix")+Loader.s("Enchant.NoEnchant")
						.replace("%enchant%", args[0]).replace("%level%", "0").replace("%item%", a.name()), s);
						return true;
					}

						Loader.msg(Loader.s("Prefix")+Loader.s("Enchant.EnchantNotExist")
						.replace("%enchant%", args[0]).replace("%level%", "0").replace("%item%", a.name()), s);
						return true;
					}
					Loader.msg(Loader.s("Prefix")+Loader.s("Enchant.HandIsEmpty")
					.replace("%enchant%", "none").replace("%level%", "none").replace("%item%", "none"), s);
					return true;
				}
			Loader.msg(Loader.s("ConsoleErrorMessage"), s);
			return true;
		}
		
		return true;
	}
	public void e(ItemStack hand, String enechant,CommandSender s) {
		try {
		Loader.msg(Loader.s("Prefix")+Loader.s("Enchant.EnchantRemoved")
		.replace("%enchant%", enechant).replace("%level%", String.valueOf(hand.getEnchantmentLevel(TheAPI.getEnchantmentAPI().getByName(enechant))))
		.replace("%item%", hand.getType().name()), s);
		return;
		}catch(Exception error) {
			Loader.msg(Loader.s("Prefix")+Loader.s("Enchant.EnchantNotExist")
			.replace("%enchant%", enechant).replace("%level%", "0").replace("%item%", hand.getType().name()), s);
			return;
		}}
	public boolean contains(String s, String[] args) {
		if(args[0].equalsIgnoreCase(s))return true;
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender s, Command a, String ea, String[] args) {
    	List<String> c = new ArrayList<>();
		if(a.getName().equalsIgnoreCase("enchanttableremove")||a.getName().equalsIgnoreCase("Enchantremove")||a.getName().equalsIgnoreCase("Enchantmentremove"
				+ "")) {
			if(s.hasPermission("ServerControl.Enchant")) {
		    	List<String> enchs = Arrays.asList("ARROW_DAMAGE","POWER","ARROW_FIRE", "FIRE","ARROW_INFINITE", "INFINITY", "ARROW_KNOCKBACK","PUNCH",
		    			"BINDING_CURSE","CURSE_OF_BINDING", "DAMAGE_ALL","SHARPNESS", "DAMAGE_ARTHROPODS","BANE_OF_ARTHROPODS", "DAMAGE_UNDEAD","SMITE"
		    			, "DEPTH_STRIDER"
		    			, "DIG_SPEED","EFFICIENCY", "DURABILITY","UNBREAKING", "FIRE_ASPECT","FIREASPECT", "FROST_WALKER", "KNOCKBACK"
		    			, "LOOT_BONUS_BLOCKS","LOOTBLOCKS","FORTUNE"
		    			, "LOOT_BONUS_MOBS","LOOTMOBS","LOOTING", "LUCK", "LURE", "MENDING", "OXYGEN","RESPIRATION", "PROTECTION_ENVIRONMENTAL","PROTECTION",
		    			"PROTECTION_EXPLOSIONS","BLAST_PROTECTION","ALLDAMAGE","ALL_DAMAGE","DAMAGEALL"
		    			, "PROTECTION_FALL","FEATHER_FALLING", "PROTECTION_FIRE","FIRE_PROTECTION", "PROTECTION_PROJECTILE","PROJECTILE_PROTECTION"
		    			, "SILK_TOUCH", "SWEEPING_EDGE"
		    			, "THORNS", "VANISHING_CURSE","CURSE_OF_VANISHING", "WATER_WORKER","AQUA_AFFINITY");
		    	if(TheAPI.getServerVersion().contains("1_13")||TheAPI.getServerVersion().contains("1_14")) enchs = Arrays.asList("ARROW_DAMAGE","POWER","ARROW_FIRE", "FIRE","ARROW_INFINITE", "INFINITY", "ARROW_KNOCKBACK","PUNCH",
		    			"BINDING_CURSE","CURSE_OF_BINDING", "DAMAGE_ALL","SHARPNESS", "DAMAGE_ARTHROPODS","BANE_OF_ARTHROPODS", "DAMAGE_UNDEAD","SMITE"
		    			, "DEPTH_STRIDER"
		    			, "DIG_SPEED","EFFICIENCY", "DURABILITY","UNBREAKING", "FIRE_ASPECT","FIREASPECT", "FROST_WALKER", "KNOCKBACK"
		    			, "LOOT_BONUS_BLOCKS","LOOTBLOCKS","FORTUNE"
		    			, "LOOT_BONUS_MOBS","LOOTMOBS","LOOTING", "LUCK", "LURE", "MENDING", "OXYGEN","RESPIRATION", "PROTECTION_ENVIRONMENTAL","PROTECTION",
		    			"PROTECTION_EXPLOSIONS","BLAST_PROTECTION","ALLDAMAGE","ALL_DAMAGE","DAMAGEALL"
		    			, "PROTECTION_FALL","FEATHER_FALLING", "PROTECTION_FIRE","FIRE_PROTECTION", "PROTECTION_PROJECTILE","PROJECTILE_PROTECTION"
		    			, "SILK_TOUCH", "SWEEPING_EDGE"
		    			, "THORNS", "VANISHING_CURSE","CURSE_OF_VANISHING", "WATER_WORKER","AQUA_AFFINITY","LOYALTY","PIERCING","IMPALING","MULTISHOT","QUICK_CHARGE","RIPTIDE","CHANNELING");
			if(s instanceof Player) {
				if(args.length==1) {
	            	c.addAll(StringUtil.copyPartialMatches(args[0], enchs, new ArrayList<>()));
				
		        }}}}
		return c;
	}}