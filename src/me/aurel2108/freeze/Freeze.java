package me.aurel2108.freeze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Freeze extends JavaPlugin {
	
	Logger log;
	private Set<String> toFreeze = new HashSet<String>();
	private ArrayList<String[]> toTmpFreeze = new ArrayList<String[]>();
	private Set<String> freezeAll = new HashSet<String>();
	
	String update = "";
	File configFile;
	
	public void onEnable(){
		
		log = getLogger();
		
		File configFile = new File(getDataFolder(), "config.yml");
	    if (!configFile.exists()) {
	        saveDefaultConfig();
	    }
		
		if (getConfig().getBoolean("save")) {
	        File saveFile = new File(getDataFolder(), "freezedPlayers.txt");
	        if (saveFile.exists()) {
	            try {
	                InputStream ips = new FileInputStream(saveFile);
	                InputStreamReader ipsr = new InputStreamReader(ips);
	                BufferedReader br = new BufferedReader(ipsr);
	                String line = null;
	                while((line = br.readLine()) != null) {
	                    toFreeze.add(line.trim());
	                }
	            } catch (Exception exc) {
	                log.log(Level.SEVERE, "Error while loading freezedPlayers.txt ", exc);
	            }
	        }
	        
	        saveFile = new File(getDataFolder(), "freezedTmpPlayers.txt");
	        if (saveFile.exists()) {
	            try {
	                InputStream ips = new FileInputStream(saveFile);
	                InputStreamReader ipsr = new InputStreamReader(ips);
	                BufferedReader br = new BufferedReader(ipsr);
	                String line = null;
	                while((line = br.readLine()) != null) {
	                	Pattern p = Pattern.compile("(.*):(.*);");
	    				Matcher m = p.matcher(line);
	    				while(m.find())
	    				{
	    					String array[] = {m.group(1).trim(), m.group(2).trim()};
	    					final String playerName = m.group(1).trim();
	    					final long time = Long.parseLong(m.group(2).trim());
	    					final int IDListArray = toTmpFreeze.size();
	    					if(Bukkit.getOfflinePlayer(playerName) != null)
	    					{
		    					toTmpFreeze.add(IDListArray, array);
		    					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    						@Override
		    						public void run() {
		    							OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
		    							if(player != null)
		    							{
			    							unfreeze(player);
			    							toTmpFreeze.remove(IDListArray);
			    							if(player.isOnline() && getConfig().getBoolean("message"))
				    							Bukkit.getPlayer(player.getName()).sendMessage("You are now unfreezed after " + time + " seconds.");	
			    							log.info(player.getName() + " unfreezed after " + time + " seconds.");
		    							}
		    						}
		    					}, time * 20);
		    					if(!toFreeze.contains(playerName))
		    						toFreeze.add(playerName);
	    					}
	    				}
	                }
	            } catch (Exception exc) {
	                log.log(Level.SEVERE, "Error while loading freezedTmpPlayers.txt ", exc);
	            }
	        }
		}
		
		PluginManager pm = Bukkit.getPluginManager();

		FreezeCommands.register(this);
        pm.registerEvents(new FreezeListener(this), this);
        log.info("Enabled.");
        
        log.info("Searching for update...");
        
        Download.getFile("http://files.aurel2108.eu/freezeversion.txt", "plugins/Freeze/");
        File dir = new File("plugins/Freeze");
        dir.mkdirs();
        File file = new File(dir + "/freezeversion.txt");
        String vs = "";
        try {
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          vs = br.readLine();
          br.close();
          fr.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        if(!vs.equalsIgnoreCase(getDescription().getVersion()))
        {
        	log.info("New version found : " + vs);
        	log.info("Check http://dev.bukkit.org/server-mods/freeze for more informations and for download it.");
        }
        else
        	log.info("No new update detected.");
        
	}
	
	public void reloadPlugin()
	{
		setEnabled(false);
		setEnabled(true);
	}
	
	public boolean canBeFrozen(Player player) {
		return !player.hasPermission("freeze.never");
	}

	public boolean isFrozen(Player player) {
		if (player == null) {
			return false;
		}
	
		if (freezeAll.contains(player.getWorld().getName())) {
			return canBeFrozen(player);
		}

		return toFreeze.contains(player.getName());
	}
	
	public int isTmpFrozen(Player player) {
		if (player == null)
			return -1;
		
		for(String[] string : toTmpFreeze) {
			if(player.getName().equalsIgnoreCase(string[0]))
				return Integer.parseInt(string[1]);
		}
		
		return 0;
	}

	public boolean getFreezeAll(World world) {
		return freezeAll.contains(world.getName());
	}

	public boolean toggleFreezeAll(World world) {
		if(freezeAll.contains(world.getName())) {
			freezeAll.remove(world.getName());
			return false;
		} else {
			freezeAll.add(world.getName());
			return true;
		}
	}
	
	public boolean toggleFreezeAll() {
		
		if(freezeAll.size() == Bukkit.getWorlds().size()) {
			freezeAll.clear();
			return false;
		}
		
		for(World w : Bukkit.getWorlds())
		{
			if(!freezeAll.contains(w.getName()))
			{
				freezeAll.add(w.getName());
			}
		}
		
		return true;
	}

	public boolean toggleFreeze(Player player) {
		if(toFreeze.contains(player.getName())) {
			toFreeze.remove(player.getName());
			return false;
		} else {
			toFreeze.add(player.getName());
			return true;
		}		
	}
	
	public void unfreeze(Player player) {
		if(toFreeze.contains(player.getName()))
			toFreeze.remove(player.getName());
		if(toTmpFreeze.contains(player.getName()))
			toTmpFreeze.remove(player.getName());
	}
	
	public void unfreeze(OfflinePlayer player) {
		if(toFreeze.contains(player.getName()))
			toFreeze.remove(player.getName());
		if(toTmpFreeze.contains(player.getName()))
			toTmpFreeze.remove(player.getName());
	}

	public void unfreezeAll() {
		freezeAll.clear();
		toFreeze.clear();
		toTmpFreeze.clear();
		Bukkit.getScheduler().cancelTasks(this);
	}

	public boolean temporarilyFreeze(final Player player, final int seconds) {
		if(!toFreeze.contains(player.getName()) && !toTmpFreeze.contains(player.getName()))	{
			toFreeze.add(player.getName());
			String[] array = {player.getName(), String.valueOf(seconds)};
			final int IDListArray = toTmpFreeze.size();
			toTmpFreeze.add(IDListArray, array);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						unfreeze(player);
						toTmpFreeze.remove(IDListArray);
						if(getConfig().getBoolean("message"))
							player.sendMessage("You are now unfreezed after " + seconds + " seconds.");
						log.info(player.getName() + " unfreezed after " + seconds + " seconds.");
					}
			}, seconds * 20);
			return true;
		} 
		else 
			return false;
	}
	
	public void onDisable(){
		
		if(getConfig().getBoolean("save"))
		{
			File saveFile = new File(getDataFolder(), "freezedPlayers.txt");
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
				log.log(Level.SEVERE, "Error while saving freezedPlayers.txt ", e);
			}
			
			saveFile = new File(getDataFolder(), "freezedTmpPlayers.txt");
			if(saveFile.exists())
				saveFile.delete();

			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));

				for(String[] array : toTmpFreeze)
				{
					out.write(array[0] + ":" + array[1] + ";\n");
				}
				
				out.close();
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error while saving freezedTmpPlayers.txt ", e);
			}
		}
		
		
		
		log.info("Disabled.");
	}
}
