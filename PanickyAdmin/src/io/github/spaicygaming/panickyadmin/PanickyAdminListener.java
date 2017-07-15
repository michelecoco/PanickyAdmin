package io.github.spaicygaming.panickyadmin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PanickyAdminListener implements Listener {
	
	PanickyAdmin main = PanickyAdmin.getInstance();
	
	/*
	 * Notify Updates
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (p.isOp() || p.hasPermission("panickyadmin.notify") || p.hasPermission("*")){
			if(main.updates.length == 2){
				p.sendMessage(ChatColor.GREEN + main.Separatori(31, '*'));
				p.sendMessage("§6§l[§cPanickyAdmin§6] New update available:");
				p.sendMessage("§6Current version: §e" + main.getDescription().getVersion());
				p.sendMessage("§6New version: §e" + main.updates[0]);
				p.sendMessage("§6What's new: §e" + main.updates[1]);
				p.sendMessage("§6Download here: §e" + main.getProject());
				p.sendMessage(ChatColor.GREEN + main.Separatori(31, '*'));
			}
		}
	}

}
