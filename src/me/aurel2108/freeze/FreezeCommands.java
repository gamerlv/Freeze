package me.aurel2108.freeze;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommands {

	public static void register(Freeze plugin)
	{
		plugin.getCommand("freeze").setExecutor(new FreezeCommand(plugin));
		plugin.getCommand("tmpfreeze").setExecutor(new TmpFreezeCommand(plugin));
		plugin.getCommand("freezeall").setExecutor(new FreezeAllCommand(plugin));
		plugin.getCommand("unfreezeall").setExecutor(new UnfreezeAllCommand(plugin));
		plugin.getCommand("rldfreeze").setExecutor(new RldFreezeCommand(plugin));
		plugin.getCommand("freezelist").setExecutor(new FreezeListCommand(plugin));
	}
	
	private static class FreezeCommand implements CommandExecutor {
		
		private final Freeze plugin;
		
		public FreezeCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			if(args.length < 1)
				return false;
			
			for (String i : args)
			{
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(p.getName().toLowerCase().contains(i.toLowerCase()) || p.getName().equalsIgnoreCase(i))
					{
						if(plugin.canBeFrozen(p))
						{
							if (plugin.toggleFreeze(p))
							{
								if(plugin.getConfig().getBoolean("message"))
									p.sendMessage("You are now freezed.");
								
								if(sender instanceof Player)
								{
									player.sendMessage(p.getName() + " freezed.");
									plugin.log.info(p.getName() + " freezed by " + player.getName());
								}
								else
									plugin.log.info(p.getName() + " freezed by Console");
							}
							else
							{
								if(plugin.getConfig().getBoolean("message"))
									p.sendMessage("You are now unfreezed.");
								
								if(sender instanceof Player)
								{
									player.sendMessage(p.getName() + " unfreezed.");
									plugin.log.info(p.getName() + " unfreezed by " + player.getName());
								}
								else
									plugin.log.info(p.getName() + " unfreezed by Console");
							}
						}
					}
				}
			}
			
			return true;
		}
		
	}
	
	private static class TmpFreezeCommand implements CommandExecutor {
		
		private final Freeze plugin;
		
		public TmpFreezeCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			if(args.length < 2)
				return false;
		
			int seconds = 0;
			if(!isNumber(args[1]) || Integer.parseInt(args[1]) <= 0) {
				
				Pattern p = Pattern.compile("(\\d)+d");
				Matcher m = p.matcher(args[1]);
				
				if(m.find())
					seconds += 86400 * Integer.parseInt(m.group(1).trim());
				
				p = Pattern.compile("(\\d)+h");
				m = p.matcher(args[1]);
				
				if(m.find())
					seconds += 3600 * Integer.parseInt(m.group(1).trim());
				
				p = Pattern.compile("(\\d)+m");
				m = p.matcher(args[1]);
				
				if(m.find())
					seconds += 60 * Integer.parseInt(m.group(1).trim());
				
				p = Pattern.compile("(\\d)+s");
				m = p.matcher(args[1]);

				if(m.find())
					seconds += Integer.parseInt(m.group(1).trim());
				
				if(seconds <= 0) {
					player.sendMessage(ChatColor.RED + "Invalid time. (Must be larger than 0)");
					return true;
				}
			}
			else
				seconds = Integer.parseInt(args[1]);
			
			for(final Player p : Bukkit.getOnlinePlayers())
			{
				if(p.getName().toLowerCase().contains(args[0].toLowerCase()) || p.getName().equalsIgnoreCase(args[0]))
				{
					if(plugin.temporarilyFreeze(p, seconds)) {
						if(plugin.getConfig().getBoolean("message"))
							p.sendMessage("You are now freezed for " + seconds + " seconds.");
						if(sender instanceof Player)
						{
							player.sendMessage(p.getName() + " freezed for " + seconds + " seconds.");
							plugin.log.info(p.getName() + " freezed by " + player.getName() + " for " +  seconds + " seconds.");
						}
						else
							plugin.log.info(p.getName() + " freezed by Console for " + seconds + " seconds.");
					}
				}
			}
			
			return true;
		}
	}	

	private static class FreezeAllCommand implements CommandExecutor {
		private final Freeze plugin;
		
		public FreezeAllCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			if(args.length < 1)
			{
				if(plugin.toggleFreezeAll())
				{
					if(plugin.getConfig().getBoolean("message"))
						Bukkit.broadcastMessage("All players are now freezed.");
					
					if(sender instanceof Player)
					{
						player.sendMessage("All players are now freezed.");
						plugin.log.info("All players freezed by " + player.getName());
					}
					else
						plugin.log.info("All players freezed by Console");
				}
				else
				{
					if(plugin.getConfig().getBoolean("message"))
						Bukkit.broadcastMessage("All players are now unfreezed.");
					
					if(sender instanceof Player)
					{
						player.sendMessage("All players are now unfreezed.");
						plugin.log.info("All players unfreezed by " + player.getName());
					}
					else
						plugin.log.info("All players unfreezed by Console");
				}
			}
			else
			{
				for (int i = 0; i < args.length; i++) {
					String arg = args[i];
					if(Bukkit.getWorld(arg) != null)
					{
						if(plugin.toggleFreezeAll(Bukkit.getWorld(arg)))
						{
							if(plugin.getConfig().getBoolean("message"))
								Bukkit.broadcastMessage("All players in " + arg + " are now freezed.");
							
							if(sender instanceof Player)
							{
								player.sendMessage("All players in " + arg + " are now freezed.");
								plugin.log.info("All players in " + arg + " freezed by " + player.getName());
							}
							else
								plugin.log.info("All players in " + arg + " freezed by Console");
						}
						else
						{
							if(plugin.getConfig().getBoolean("message"))
								Bukkit.broadcastMessage("All players in " + arg + " are now unfreezed.");
							
							if(sender instanceof Player)
							{
								player.sendMessage("All players in " + arg + " are now unfreezed.");
								plugin.log.info("All players in " + arg + " unfreezed by " + player.getName());
							}
							else
								plugin.log.info("All players in " + arg + " unfreezed by Console");
						}
					}
				}
			}
			
			return true;
		}
	}
	
	private static class UnfreezeAllCommand implements CommandExecutor {
		
		private final Freeze plugin;
		
		public UnfreezeAllCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{	
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			plugin.unfreezeAll();
			
			if(plugin.getConfig().getBoolean("message"))
				Bukkit.broadcastMessage("All players are now unfreezed.");
			
			if(sender instanceof Player)
			{
				player.sendMessage("All players are now unfreezed.");
				plugin.log.info("All players unfreezed by " + player.getName());
			}
			else
				plugin.log.info("All players unfreezed by Console");
			
			return true;
		}
	}
	
	private static class RldFreezeCommand implements CommandExecutor {
		
		private final Freeze plugin;
		
		public RldFreezeCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			plugin.reloadPlugin();
			if(sender instanceof Player) {
				player.sendMessage(plugin.getDescription().getName() + " reloaded.");
				plugin.log.info("Reloaded by " + player.getName());
			}
			else
				plugin.log.info("Reloaded by Console");
			
			return true;
		}
	}
	
	private static class FreezeListCommand implements CommandExecutor {
		
		private final Freeze plugin;
		
		public FreezeListCommand(Freeze plugin) {
			this.plugin = plugin;
		}
		
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
		{
			Player player = null;
			if(sender instanceof Player)
				player = (Player)sender;
			
			String freezedList = "";
			String tmpFreezedList = "";
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(plugin.isFrozen(p)) {
					if(freezedList.length() > 0)
						freezedList = freezedList + ", " + p.getName();
					else
						freezedList = p.getName();
				}
				
				if(plugin.isTmpFrozen(p) > 0) {
					if(tmpFreezedList.length() > 0)
						tmpFreezedList = tmpFreezedList + ", " + p.getName() + " for " + plugin.isTmpFrozen(p) + " seconds";
					else
						tmpFreezedList = p.getName() + " for " + plugin.isTmpFrozen(p) + " seconds";	
				}
			}
			
			if(sender instanceof Player) {
				if(freezedList.length() > 0)
					player.sendMessage("Freezed players : " + freezedList);
				else
					player.sendMessage("There is no freezed players.");
				
				if(tmpFreezedList.length() > 0)
					player.sendMessage("Temporarily freezed players :" + tmpFreezedList);
				else
					player.sendMessage("There is no temporarily freezed players.");
			}
			else
			{
				if(freezedList.length() > 0)
					plugin.log.info("Freezed players : " + freezedList);
				else
					plugin.log.info("There is no freezed players.");
				
				if(tmpFreezedList.length() > 0)
					plugin.log.info("Temporarily freezed players : " + tmpFreezedList);
				else
					plugin.log.info("There is no temporarily freezed players.");
			}
				
			
			return true;
		}
	}
	
	public static boolean isNumber(String in) {
        try {
            Integer.parseInt(in);        
        } catch (NumberFormatException ex) {
            return false;
        }     
        return true;
    }	
	
	private FreezeCommands() {
	}

}
