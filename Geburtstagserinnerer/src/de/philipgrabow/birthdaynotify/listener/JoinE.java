package de.philipgrabow.birthdaynotify.listener;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinE implements Listener {

	File file = new File("plugins/GeburtstagsE/Geburtstage", "Geburtstag.yml");
	File uidfile = new File("plugins/GeburtstagsE/UUID", "UUID.yml");

	@SuppressWarnings({ "deprecation" })
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		UUIDcreate(e);
		Date date = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//		String inputdate = "09.09.2017";
//		Date date = null;
//		try {
//			date = format.parse(inputdate);
//		} catch (ParseException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		format.format(date);
		for (Player p : e.getPlayer().getServer().getOnlinePlayers()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (cfg.contains(p.getUniqueId().toString())) {
				String datecfg = cfg.getString(p.getUniqueId().toString() + ".Geburtstag.Datum");
				SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
				Date date1 = null;
				try {
					date1 = format1.parse(datecfg);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				format1.format(date1);
				if (date.getDate() == date1.getDate() && date.getMonth() == date1.getMonth()) {
//				if (
					int thisyear = date.getYear();
					int birthyear = date1.getYear();
					int age = thisyear - birthyear;
					Bukkit.broadcastMessage(ChatColor.RED + "[GEBURTSTAGS-ERINNERER] Der Spieler " + p.getName()
							+ " hat heute Geburtstag!");
					Bukkit.broadcastMessage(ChatColor.RED + "[GEBURTSTAGS-ERINNERER] Er wird " + age + " Jahre alt!");
					Bukkit.broadcastMessage(
							ChatColor.RED + "[GEBURTSTAGS-ERINNERER] Sende diesem Spieler Glückwünsche!");
				}
			}
		}
		return;
	}

	public void UUIDcreate(PlayerJoinEvent e) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(uidfile);
		String uid = e.getPlayer().getUniqueId().toString();
		cfg.set(e.getPlayer().getName() + ".UUID", uid);
		try {
			cfg.save(uidfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
