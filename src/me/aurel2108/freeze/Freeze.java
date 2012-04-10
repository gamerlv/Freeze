package me.aurel2108.freeze;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Freeze extends JavaPlugin {
	
	public static Logger log = Logger.getLogger("Minecraft");
	public static ArrayList<String> toFreeze = new ArrayList<String>();
	public static boolean freezeAll = false;
	PluginDescriptionFile pdfFile;
	private FreezePlayerListener playerListener = new FreezePlayerListener();
	
	File configFile;
	public static FileConfiguration config;
	
	public void onEnable(){
		
		pdfFile = this.getDescription();
		if(!(new File("plugins/" + pdfFile.getName()).exists()))
			new File("plugins/" + pdfFile.getName()).mkdir();
		
		configFile = new File("plugins/" + pdfFile.getName() + "/config.yml");
		config = new YamlConfiguration();
		
		if(!configFile.exists()){
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	    }
		
		try {
			config.load(configFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
					freezeAll = false;
					player.sendMessage("All players are now unfreezed.");
				}
				else
				{
					freezeAll = true;
					player.sendMessage("All players are now freezed.");
				}
			}
			return true;
		}
		
		if(command.getName().equalsIgnoreCase("unfreezeall"))
		{
			if(player.isOp() || player.hasPermission("freeze.unfreezeall"))
			{
				freezeAll = false;
				toFreeze.clear();
				player.sendMessage("All players are now unfreezed.");
			}
			return true;
		}
		
		return false;
	}

	public void onDisable(){
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info(pdfFile.getName() + " disabled.");
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
