package me.aurel2108.freeze;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Freeze extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	public static ArrayList<String> toFreeze = new ArrayList<String>();
	
	private FreezePlayerListener playerListener = new FreezePlayerListener();
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		log.info("Freeze version "+ this.getDescription().getVersion() +" enabled.");

        pm.registerEvents(playerListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
	 
		if (command.getName().equalsIgnoreCase("freeze") && (player.isOp() || player.hasPermission("freeze.freeze"))){
			if(args.length > 0)
			{
				for (String i : args)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						//if arg is set to * we want to freeze everyone, but not ourselves of course.
						if( ( i.toLowerCase() == "*" && i.toLowerCase() != player.getName().toLowerCase() ) || p.getName().toLowerCase().contains(i.toLowerCase()) || p.getName().equalsIgnoreCase(i))
						{
							if(toFreeze.size() == 0 || toFreeze.contains(p))
							{
								toFreeze.add(p.getName());
								player.sendMessage(p.getName() + " freezed.");
							}
							else
							{
								toFreeze.remove(p.getName());
								player.sendMessage(p.getName() + " unfreezed.");
							}
						}
					}
				}
				return true;
			}
			else
				player.sendMessage("Usage : /freeze <player name>"); return false;
		}
		return false;
	}
 
	

	public void onDisable(){
		log.info("Freeze version v"+ this.getDescription().getVersion() +" disabled.");
	}

}
