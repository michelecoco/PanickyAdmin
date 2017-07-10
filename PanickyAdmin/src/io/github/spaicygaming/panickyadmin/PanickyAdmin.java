package io.github.spaicygaming.panickyadmin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PanickyAdmin extends JavaPlugin implements Listener{
	
	private static PanickyAdmin instance;
	
	boolean checkUpdates = getConfig().getBoolean("CheckForUpdates");
	public Object[] updates;
	
	public void onEnable(){
		instance = this;
		getLogger().info("PanickyAdmin has been Enabled!");
		
		if (!getConfig().getString("ConfigVersion").equals("1.1")) {
	        getServer().getConsoleSender().sendMessage("[PanickyAdmin] " + ChatColor.RED + "OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	    }
		
		getCommand("panickyadmin").setExecutor(new PanickyAdminCommands());
		getCommand("panic").setExecutor(new PanickyAdminCommands());
		
		saveDefaultConfig();
		
		updates = UpdateChecker.getLastUpdate();
		
		if (checkUpdates){
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

	public static PanickyAdmin getInstance(){
		return instance;
	}
	
	public void onDisable(){
		getLogger().info("PanickyAdmin has been Disabled!");
	}
	
	/*
	 * Notify Updates
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (p.isOp() || p.hasPermission("panickyadmin.notify") || p.hasPermission("*")){
			if(updates.length == 2){
				p.sendMessage(ChatColor.GREEN + Separatori(31));
				p.sendMessage("§6§l[§cPanickyAdmin§6] New update available:");
				p.sendMessage("§6Current version: §e" + getDescription().getVersion());
				p.sendMessage("§6New version: §e" + updates[0]);
				p.sendMessage("§6What's new: §e" + updates[1]);
				p.sendMessage(ChatColor.GREEN + Separatori(31));
			}
		}
	}
	
	private String Separatori(int value){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value; i++){
			sb.append("=");
		}
		return sb.toString();
	}
	
	public String getProject(){
		return "https://www.spigotmc.org/resources/panickyadmin.43625/";
	}
	
}
