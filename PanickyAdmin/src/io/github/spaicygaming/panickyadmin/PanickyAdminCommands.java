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
	
	private List<String> commands = main.getConfig().getStringList("Commands");
	
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
					s.sendMessage(ChatColor.AQUA + "   /panickyadmin list " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Lists all commands");
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
						s.sendMessage(prefix + ChatColor.RED + "Config Reloaded.");
					}
					else {
						s.sendMessage(noperms);
					}
				}
				//LIST
				else if (args[0].equalsIgnoreCase("list")){
					if (!s.hasPermission("panickyadmin.list")){
						s.sendMessage(noperms);
						return true;
					}
					displayCommands(s);
				}
				
				//ELSE
				else{
					s.sendMessage(unkncmd);
				}
			}
			
			else{
				s.sendMessage(unkncmd);
			}
		}
		
		if (alias.equalsIgnoreCase("panic")){
			if (!s.hasPermission("panickyadmin.panic")){
				s.sendMessage(noperms);
				return true;
			}
			onPanic(s);
			s.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Messages.panic")));
			
		}
		return false;
	}
	
	private void onPanic(CommandSender s){
		for (String cmd : commands){
			main.getServer().dispatchCommand(s, cmd);
		}
	}
	
	private void displayCommands(CommandSender s){
		s.sendMessage(ChatColor.RED + "  --=" + ChatColor.GOLD  + " PanickyAdmin " + ChatColor.GRAY + "List" + ChatColor.RED + " =--");
		int index = 1;
		for (String cmd : commands){
			s.sendMessage(ChatColor.GRAY + " " + index + ")" + ChatColor.AQUA + " " + cmd);
			index++;
		}
		s.sendMessage(ChatColor.RED + "      --=-=-=-=-=-=--");

	}

}
