package me.hawk.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin { 
	
	
	
	

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warn")) {
			// Check for permissions
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "/warn <player> <reason>");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
				return true;
				
			}
			
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "Something went wrong with the command. You missed and arguement!");
				return true;
			}
			
			String msg = "";
			for (int i = 1; i < args.length; i++) {
				msg += args[i] + " ";
			}
			
			Object level = this.getConfig().get(target.getName());
			
			if (level == null) {
				target.sendMessage(ChatColor.RED + "You were warned by " + ChatColor.GOLD + sender.getName() +  ChatColor.RED + " for " + ChatColor.GOLD + msg);
				this.getConfig().set(target.getName(),  1);
				this.saveConfig();
				return true;
				 
			}
			int l = Integer.parseInt(level.toString());
				
			if (l == 1) {
				target.kickPlayer(ChatColor.RED + "You have been kicked by " + ChatColor.GOLD + sender.getName() + ChatColor.RED + " for " + ChatColor.GOLD + msg);
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Player " + ChatColor.GOLD + target.getName() + ChatColor.RED + " has been kicked for " + ChatColor.GOLD + msg);
				this.getConfig().set(target.getName(),  2);
				this.saveConfig();
				return true;
				
			}
			
			if (l == 2) {
				target.kickPlayer(ChatColor.RED + "You have been temp banned for " + ChatColor.GOLD +  ChatColor.BOLD + "1 minute" + ChatColor.RESET + ChatColor.RED + " by " + ChatColor.GOLD + sender.getName() + ChatColor.RED + " for " + ChatColor.GOLD + msg);
				target.setBanned(true);
				this.getConfig().set(target.getName(), 3);
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Hawk" + ChatColor.BOLD + ChatColor.GOLD + "Warn > " + ChatColor.RESET + ChatColor.RED + "Player " + target.getName() + " has been temp banned for " + ChatColor.GOLD +  ChatColor.BOLD + "1 minute" + ChatColor.RESET + ChatColor.RED + " by " + ChatColor.GOLD + sender.getName() + ChatColor.RED + " for " + ChatColor.GOLD + msg);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						target.setBanned(false);
					}
				}, 60 * 20);
				return true;
			}
			if (l == 3) {
				target.kickPlayer(ChatColor.RED + "You have been temp banned for " + ChatColor.GOLD + ChatColor.BOLD + "1 day " + ChatColor.RESET + ChatColor.RED + "by " + ChatColor.GOLD + sender.getName() + ChatColor.RED + " for " + ChatColor.GOLD + msg);
				target.setBanned(true);
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Hawk" + ChatColor.BOLD + ChatColor.GOLD + "Warn > " + ChatColor.RESET + ChatColor.RED + "Player " + target.getName() + " has been temp banned for " + ChatColor.GOLD +  ChatColor.BOLD + "1 day" + ChatColor.RESET + ChatColor.RED + " by " + ChatColor.GOLD + sender.getName() + ChatColor.RED + " for " + ChatColor.GOLD + msg);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						target.setBanned(false);
					}
				}, 86400 * 20);
				return true;
			}
		}
		return true;
		
	}
	
}
