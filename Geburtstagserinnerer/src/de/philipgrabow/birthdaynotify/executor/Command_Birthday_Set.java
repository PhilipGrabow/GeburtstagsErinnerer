package de.philipgrabow.birthdaynotify.executor;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Command_Birthday_Set implements CommandExecutor {
	
	File file = new File("plugins/GeburtstagsE/Geburtstage", "Geburtstag.yml");
	File uidfile = new File("plugins/GeburtstagsE/UUID", "UUID.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("birthday")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					Player p = (Player) sender;
					String uid = p.getUniqueId().toString();
					String pathday = uid + ".Geburtstag.Datum";
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					if (cfg.contains(uid)) {
						p.sendMessage(ChatColor.GOLD + "Dein Geburtstag ist am : " + cfg.getString(pathday));
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Kein Geburtstag vorhanden!");
						return true;
					}
				}
				if (args.length == 1) {
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(uidfile);
					if (cfg.contains(args[0])) {
						Player p2 = Bukkit.getPlayer(UUID.fromString(args[0] + ".UUID"));
						Player p = (Player) sender;
						String pathday = p2.getUniqueId().toString() + ".Geburtstag.Datum";
						FileConfiguration cfg2 = YamlConfiguration.loadConfiguration(file);
						if (cfg2.contains(p2.getUniqueId().toString())) {
							p.sendMessage(ChatColor.GOLD + "Der Geburtstag von " + p2.getName() 
							+ " ist am :" + cfg2.getString(pathday));
							return true;
						} else {
							p.sendMessage(ChatColor.RED + "Kein Geburtstag vorhanden!");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Spieler nicht vorhanden!");
						return true;
					}
				}
				if(args.length > 1) {
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Du bist kein Spieler");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("setbirthday")) {
			if(sender instanceof Player) {
				if(args.length == 2) {
					Player p = (Player) sender;
					FileConfiguration uidcfg = YamlConfiguration.loadConfiguration(uidfile);
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					if(uidcfg.contains(args[0])) {
						Player p2 = Bukkit.getPlayer(UUID.fromString(uidcfg.getString(args[0] + ".UUID")));
						if(cfg.contains(p2.getUniqueId().toString())) {
							p.sendMessage(ChatColor.RED + "Dein Geburtstag ist"
									+ " schon vorhanden wird somit überschrieben!");
						}
						SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
						Date date2 = null;
						try {
							date2 = format.parse(args[1]);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String outdate = format.format(date2);
						String pathday = p2.getUniqueId().toString() + ".Geburtstag.Datum";
						String pathname = p2.getUniqueId().toString() + ".Geburtstag.SpielerName";
						cfg.set(pathday, outdate);
						cfg.set(pathname, p2.getName());
						p.sendMessage(ChatColor.GOLD + "Geburtstag gespeichert!");
						try {
							cfg.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Spieler nicht vorhanden!");
						return true;
					}
				}
				if(args.length != 2) {
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Du bist kein Spieler!");
				return true;
			}
		}
		return false;
	}

}
