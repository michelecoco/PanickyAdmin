package io.github.spaicygaming.panickyadmin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PanickyAdmin extends JavaPlugin{

	private static PanickyAdmin instance;
	boolean checkUpdates = getConfig().getBoolean("CheckUpdates");
	public Object[] updates;
	
	private List<String> paniccmds;
	private List<String> unpaniccmds;
	private String cmdsExecutor = null;
	
	public void onEnable(){
		instance = this;
		
		saveDefaultConfig();
		refreshLists();
		
		getLogger().info("PanickyAdmin has been Enabled!");
		
		//Check config version
		ConsoleCommandSender cs = getServer().getConsoleSender();
		if (!getConfig().getString("ConfigVersion").equals("1.4")) {
			cs.sendMessage(Separatori(50, '='));
	        cs.sendMessage("[PanickyAdmin] " + ChatColor.RED + "OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	        cs.sendMessage(Separatori(50, '='));
	    }
		
		//Get commands executor
		String cmdsExecConfig = getConfig().getString("Commands.cmdsExecutor");
		if (cmdsExecConfig.equalsIgnoreCase("player") || cmdsExecConfig.equalsIgnoreCase("console")){
			cmdsExecutor = cmdsExecConfig.toUpperCase();
		}
		else{
			cs.sendMessage(Separatori(50, '='));
	        cs.sendMessage("[PanickyAdmin] " + ChatColor.RED + "ERROR! Invalid commands executor(Commands.cmdsExecutor). Insert 'player' or 'console'!");
	        cs.sendMessage(Separatori(50, '='));
	        //cmdsExecutor rimane nullo
		}
		
		getCommand("panickyadmin").setExecutor(new PanickyAdminCommands());
		getCommand("panic").setExecutor(new PanickyAdminCommands());
		getCommand("unpanic").setExecutor(new PanickyAdminCommands());
		
		updates = UpdateChecker.getLastUpdate();
		
		if (checkUpdates){
			getServer().getPluginManager().registerEvents(new PanickyAdminListener(), this);
			getLogger().info("Checking for updates...");
			
			if (updates.length == 2){
				getLogger().info(Separatori(70, '='));
				getLogger().info("Update found! Download here: " + getProject());
				getLogger().info("New version: " + updates[0]);
				getLogger().info("What's new: " + updates[1]);
				getLogger().info(Separatori(70, '='));
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

	/**
	 * Gets panic command list
	 * @return panic commands list
	 */
	public List<String> getPanicCommands() {
		return paniccmds;
	}
	
	/**
	 * Gets unpanic command list
	 * @return unpanic commands list
	 */
	public List<String> getUnpanicCommands(){
		return unpaniccmds;
	}
	
	public String getCommandsExecutor(){
		return cmdsExecutor;
	}
	
	/**
	 * Refresh the lists
	 */
	public void refreshLists(){
		paniccmds = getConfig().getStringList("Commands.panic");
		unpaniccmds = getConfig().getStringList("Commands.unpanic");
	}
	
	/**
	 * Create a 'separator' string
	 * @param value Number of characters
	 * @param charValue Type of character
	 * @return The separator string
	 */
	String Separatori(int value, char charValue){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value; i++){
			sb.append(charValue);
		}
		return sb.toString();
	}
	
	/**
	 * Gets project page link
	 * @return spigot page link
	 */
	String getProject(){
		return getDescription().getWebsite();
	}


}
