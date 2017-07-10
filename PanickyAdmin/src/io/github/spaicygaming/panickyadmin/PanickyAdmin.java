package io.github.spaicygaming.panickyadmin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PanickyAdmin extends JavaPlugin {
	
	private static PanickyAdmin instance;
	
	public void onEnable(){
		instance = this;
		getLogger().info("PanickyAdmin has been Enabled!");
		
		if (!getConfig().getString("ConfigVersion").equals("1.0")) {
	        getServer().getConsoleSender().sendMessage("[ConsoleOnly] " + ChatColor.RED + "OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	    }
		
		getCommand("panickyadmin").setExecutor(new PanickyAdminCommands());
		getCommand("panic").setExecutor(new PanickyAdminCommands());
		
		saveDefaultConfig();
	}

	public static PanickyAdmin getInstance(){
		return instance;
	}
	
	public void onDisable(){
		getLogger().info("PanickyAdmin has been Disabled!");
	}
	
}
