package me.aurel2108.freeze;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Freeze extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	public static ArrayList<String> toFreeze = new ArrayList<String>();
	public static boolean freezeAll = false;
	PluginDescriptionFile pdfFile = this.getDescription();
	
	private FreezePlayerListener playerListener = new FreezePlayerListener();
	
	public void onEnable(){
		log.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " enabled.");
		PluginManager pm = getServer().getPluginManager();

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
						if(p.getName().toLowerCase().contains(i.toLowerCase()) || p.getName().equalsIgnoreCase(i))
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
		
		if(command.getName().equalsIgnoreCase("freezeall"))
		{
			if(player.isOp() || player.hasPermission("freeze.freezeall"))
			{
				if(freezeAll)
				{
					freezeAll = true;
					player.sendMessage("All players are now freezed.");
				}
				else
				{
					freezeAll = false;
					player.sendMessage("All players are now unfreezed.");
				}
			}
		}
		return false;
	}
 
	

	public void onDisable(){
		log.info(pdfFile.getName() + " disabled.");
	}

}
