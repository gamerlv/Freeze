package me.aurel2108.freeze;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class FreezePlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler	
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && event.getMessage().startsWith("/") == false && Freeze.config.getBoolean("speak") == false)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if(event.isCancelled() == false && event.getPlayer() != null && Freeze.config.getBoolean("commands") == false)
		{
			if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
			else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false && event.getPlayer().isOp() == false))
			{
				event.setCancelled(true);
			}
		}
	}

}
