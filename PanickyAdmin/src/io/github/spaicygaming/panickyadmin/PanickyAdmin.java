package io.github.spaicygaming.panickyadmin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PanickyAdmin extends JavaPlugin{
	
	private static PanickyAdmin instance;
	public static PanickyAdmin getInstance(){
		return instance;
	}
	
	boolean checkUpdates = getConfig().getBoolean("CheckUpdates");
	public Object[] updates;
	
	private List<String> commands;
	
	public void onEnable(){
		instance = this;
		refreshList();
		
		getLogger().info("PanickyAdmin has been Enabled!");
		
		if (!getConfig().getString("ConfigVersion").equals("1.1")) {
	        getServer().getConsoleSender().sendMessage("[PanickyAdmin] " + ChatColor.RED + "OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	    }
		
		getCommand("panickyadmin").setExecutor(new PanickyAdminCommands());
		getCommand("panic").setExecutor(new PanickyAdminCommands());
		
		
		saveDefaultConfig();
		
		updates = UpdateChecker.getLastUpdate();
		
		if (checkUpdates){
			getServer().getPluginManager().registerEvents(new PanickyAdminListener(), this);
			getLogger().info("Checking for updates...");
			
			if (updates.length == 2){
				getLogger().info(Separatori(70));
				getLogger().info("Update found! Download here: " + getProject());
				getLogger().info("New version: " + updates[0]);
				getLogger().info("What's new: " + updates[1]);
				getLogger().info(Separatori(70));
			} else {
				getLogger().info("No new version available." );
			}
		}	
	}

	public void onDisable(){
		getLogger().info("PanickyAdmin has been Disabled!");
	}

	public List<String> getCommands() {
		return commands;
	}
	
	public void refreshList(){
		commands = getConfig().getStringList("Commands");
	}
	
	String Separatori(int value){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value; i++){
			sb.append("=");
		}
		return sb.toString();
	}
	
	String getProject(){
		return getDescription().getWebsite();
	}


}
