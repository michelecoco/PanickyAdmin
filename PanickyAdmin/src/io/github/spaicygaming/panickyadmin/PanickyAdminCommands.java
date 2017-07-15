package io.github.spaicygaming.panickyadmin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PanickyAdminCommands implements CommandExecutor{
	
	PanickyAdmin main = PanickyAdmin.getInstance();
	
	private String prefix = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.Prefix")) + ChatColor.RESET + " ";
	private String unkncmd = prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.unknwCmd"));
	private String noperms = prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.noPerms"));
	private String ver = main.getDescription().getVersion();
	
	public boolean onCommand(CommandSender s, Command cmd, String alias, String[] args){
		
		if (alias.equalsIgnoreCase("panickyadmin")){
			if (args.length == 0){
				s.sendMessage(unkncmd);
			}
			
			else if (args.length == 1){
				//HELP
				if (args[0].equalsIgnoreCase("help")){
					s.sendMessage("");
					s.sendMessage(ChatColor.RED + "   --=-=" + ChatColor.GOLD  + " PanickyAdmin " + ChatColor.GRAY + ver + ChatColor.RED + " =-=--");
					s.sendMessage(ChatColor.AQUA + "   /panic " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Executes all commands");
					s.sendMessage(ChatColor.AQUA + "   /panickyadmin info " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Shows Info");
					s.sendMessage(ChatColor.AQUA + "   /panickyadmin reload " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Reloads the Config");
					s.sendMessage(ChatColor.AQUA + "   /panickyadmin list [panic/unpanic]" + ChatColor.GREEN + "->" + ChatColor.GRAY + " Lists all commands");
					s.sendMessage(ChatColor.RED + "         --=-=-=-=-=-=--");
					s.sendMessage("");
				}
				//INFO
				else if (args[0].equalsIgnoreCase("info")){
					s.sendMessage(ChatColor.DARK_GREEN + "     --=-=-=-=-=-=-=-=-=--");
					s.sendMessage(ChatColor.GOLD + "        PanickyAdmin " + ChatColor.GRAY + "v" + ver);
					s.sendMessage(ChatColor.GOLD + "      Author: " + ChatColor.AQUA + "SpaicyGaming");
					s.sendMessage(ChatColor.RED + "   Project: " + ChatColor.ITALIC + main.getProject());
					s.sendMessage(ChatColor.RED + "   SourceCode: " + ChatColor.ITALIC + "https://github.com/SpaicyGaming/PanickyAdmin");
					s.sendMessage(ChatColor.DARK_GREEN + "       --=-=-=-=-=-=-=--");
					s.sendMessage("");
				}
				//RELOAD
				else if (args[0].equalsIgnoreCase("reload")){
					if (s.hasPermission("panickyadmin.reload")){
						main.reloadConfig();
						main.refreshLists();
						s.sendMessage(prefix + ChatColor.RED + "Config Reloaded.");
					}
					else {
						s.sendMessage(noperms);
					}
				}
				
				//ELSE
				else{
					s.sendMessage(unkncmd);
				}
			}
			
			else if (args.length == 2){
				//LISTS
				if (args[0].equalsIgnoreCase("list")){
					if (!s.hasPermission("panickyadmin.list")){
						s.sendMessage(noperms);
						return true;
					}
					
					if (args[1].equalsIgnoreCase("panic") || args[1].equalsIgnoreCase("unpanic")){
						displayCommands(s, args[1].toLowerCase());
					}

					else{
						s.sendMessage(unkncmd);
					}
				}
				//ELSE
				else{
					s.sendMessage(unkncmd);
				}
			}
			
			//IF ARGS > 2
			else{
				s.sendMessage(unkncmd);
			}
			
			
		}
		
		if (alias.equalsIgnoreCase("panic")){
			if (!s.hasPermission("panickyadmin.panic")){
				s.sendMessage(noperms);
				return true;
			}
			dispatchAll(s, "panic");;
			s.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.panic")));
			
		}
		
		if (alias.equalsIgnoreCase("unpanic")){
			if (!s.hasPermission("panickyadmin.unpanic")){
				s.sendMessage(noperms);
				return true;
			}
			dispatchAll(s, "unpanic");;
			s.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.unpanic")));
			
		}
		
		return false;
	}
	
	/**
	 * Dispatch all commands
	 * @param s Command Sender
	 * @param listValue List name [panic || unpanic]
	 */
	private void dispatchAll(CommandSender s, String listValue){
		List<String> commandslist = null;
		
		if (listValue.equals("panic")){
			commandslist = main.getPanicCommands();
		}
		else if (listValue.equals("unpanic")){
			commandslist = main.getUnpanicCommands();
		}else{
			main.getLogger().info(main.Separatori(50, '*'));
			main.getLogger().info("ERROR: Unknown list! Please, PM the author (SpaicyGaming): " + main.getProject());
			main.getLogger().info(main.Separatori(50, '*'));
		}
		
		for (String cmd : commandslist){
			main.getServer().dispatchCommand(s, cmd);
		}
	}
	
	/**
	 * Display all commands
	 * @param s Command Sender
	 * @param list Config List (panic || unpanic)
	 */
	private void displayCommands(CommandSender s, String list){
		s.sendMessage(ChatColor.RED + "  --=" + ChatColor.GOLD  + " PanickyAdmin " + ChatColor.GRAY + list + "-List" + ChatColor.RED + " =--");
		List<String> commandslist;
		
		if (list.toLowerCase().equals("panic")){
			commandslist = main.getPanicCommands();
		}//se è diverso da panic è per forza unpanic
		else{
			commandslist = main.getUnpanicCommands();
		}
		
		int index = 1;
		for (String cmd : commandslist){
			s.sendMessage(ChatColor.GRAY + " " + index + ")" + ChatColor.AQUA + " " + cmd);
			index++;
		}
		s.sendMessage(ChatColor.RED + "      --=-=-=-=-=-=--");

	}

}
