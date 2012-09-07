package me.aurel2108.freeze;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class FreezeListener implements Listener {
	
	private final Freeze plugin;
	
	public FreezeListener(Freeze plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(plugin.isFrozen(event.getPlayer())) {
			
			if(!plugin.getConfig().getBoolean("lookaround"))
				event.setTo(event.getFrom());
			else {
				if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ())
				{
					Location loc = event.getFrom();
					loc.setPitch(event.getTo().getPitch());
					loc.setYaw(event.getTo().getYaw());
					event.getPlayer().teleport(loc);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && plugin.isFrozen(event.getPlayer()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && plugin.isFrozen(event.getPlayer()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && plugin.isFrozen(event.getPlayer()))
			event.setCancelled(true);
	}
	
	@EventHandler	
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && plugin.isFrozen(event.getPlayer()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		if(plugin.isFrozen(event.getPlayer())) {
			
			if(!plugin.getConfig().getBoolean("lookaround"))
				event.setTo(event.getFrom());
			else {
				if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ())
				{
					Location loc = event.getFrom();
					loc.setPitch(event.getTo().getPitch());
					loc.setYaw(event.getTo().getYaw());
					event.getPlayer().teleport(loc);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && event.getMessage().startsWith("/") == false && plugin.getConfig().getBoolean("speak") == false && plugin.isFrozen(event.getPlayer()))
			event.setCancelled(true);

	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && plugin.isFrozen(event.getPlayer()) && plugin.getConfig().getBoolean("commands") == false)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event)
	{
		if(event.getEntity() != null && event.getEntity() instanceof Player && !event.isCancelled() && plugin.getConfig().getBoolean("damage"))
		{
			Player player = (Player)event.getEntity();
			if(plugin.isFrozen(player))
			{
				event.setDamage(0);
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if(plugin.getConfig().getBoolean("alertupdate") && event.getPlayer() != null && (event.getPlayer().isOp() || event.getPlayer().hasPermission("freeze.checkupdate")))
		{
			if(!plugin.update.isEmpty())
			{
				event.getPlayer().sendMessage(ChatColor.RED + "[Freeze] A new update (" + plugin.update + ") is available.");
				event.getPlayer().sendMessage(ChatColor.RED + "[Freeze] For more informations, go to the BukkitDev page : http://dev.bukkit.org/server-mods/freeze");
			}
		}
	}

}
