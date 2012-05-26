package me.aurel2108.freeze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
	public static ArrayList<String> toTmpFreeze = new ArrayList<String>();
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
		
		if(config.getBoolean("save"))
		{
			File saveFile = new File("plugins/" + pdfFile.getName() + "/freezedPlayers.txt");
			if(saveFile.exists())
			{
				try {
					InputStream ips = new FileInputStream(saveFile);
					InputStreamReader ipsr = new InputStreamReader(ips);
					BufferedReader br = new BufferedReader(ipsr);
					
					String line = null;
					
					while((line = br.readLine()) != null)
					{
						toFreeze.add(line.trim());
					}
				} catch (Exception e) {
					System.out.println("Error : " + e.toString());
				}
			}
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
							if(toFreeze.size() == 0 || !toFreeze.contains(p.getName()))
							{
								toFreeze.add(p.getName());
								player.sendMessage(p.getName() + " freezed.");
							}
							else if(toFreeze.size() > 0 && toFreeze.contains(p.getName()))
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
		
		if (command.getName().equalsIgnoreCase("tmpfreeze") && (player.isOp() || player.hasPermission("freeze.tmpfreeze"))) {
			if(args.length > 1)
			{
				for(final Player p : Bukkit.getOnlinePlayers())
				{
					if(p.getName().toLowerCase().contains(args[0].toLowerCase()) || p.getName().equalsIgnoreCase(args[0]))
					{
						if(Integer.parseInt(args[1]) > 0)
						{
							if(toFreeze.size() == 0 || !toFreeze.contains(p.getName()))
							{
								p.sendMessage("You are now freezed for " + Integer.parseInt(args[1]) + " seconds.");
								player.sendMessage("You have freeze " + p.getName() + " for " + Integer.parseInt(args[1]) + " seconds.");
								toFreeze.add(p.getName());
								toTmpFreeze.add(p.getName());
								Timer timer = new Timer();
							    timer.schedule (new TimerTask() {
							            public void run()
							            {
							                toFreeze.remove(p.getName());
							                toTmpFreeze.remove(p.getName());
							                this.cancel();
							                p.sendMessage("You are now unfreezed after " + scheduledExecutionTime() / 1000 + " seconds.");
							            }
							    }, ((Integer.parseInt(args[1])) * 1000));
							}
						}
					}
				}
				return true;
			}
			else
				return false;
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
		
		if(config.getBoolean("save"))
		{
			File saveFile = new File("plugins/" + pdfFile.getName() + "/freezedPlayers.txt");
			if(saveFile.exists())
				saveFile.delete();
			
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
					
				for(String freezedPlayer : toFreeze)
				{
					if(!toTmpFreeze.contains(freezedPlayer))
						out.write(freezedPlayer + "\n");
				}
				
				out.close();
			} catch (Exception e) {
				System.out.println("Error : " + e.toString());
			}
		}
		
		/*try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
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
