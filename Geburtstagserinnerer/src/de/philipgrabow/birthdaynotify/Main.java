package de.philipgrabow.birthdaynotify;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.philipgrabow.birthdaynotify.executor.Command_Birthday_Set;
import de.philipgrabow.birthdaynotify.listener.JoinE;

public class Main extends JavaPlugin {

//	File file = new File("plugins/GeburtstagsE/Geburtstage", "Geburtstag.yml");
//	File uidfile = new File("plugins/GeburtstagsE/UUID", "UUID.yml");

	@Override
	public void onDisable() {
		this.getLogger().info("disabled!");
	}

	@Override
	public void onEnable() {
		createFile();
		getCommand("birthday").setExecutor(new Command_Birthday_Set());
		getCommand("setbirthday").setExecutor(new Command_Birthday_Set());
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinE(), this);
		this.getLogger().info("enabled!");
	}

	public void createFile() {
		File file = new File("plugins/GeburtstagsE/Geburtstage", "Geburtstag.yml");
		File uidfile = new File("plugins/GeburtstagsE/UUID", "UUID.yml");
		YamlConfiguration.loadConfiguration(file);
		YamlConfiguration.loadConfiguration(uidfile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!uidfile.exists()) {
			try {
				uidfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
