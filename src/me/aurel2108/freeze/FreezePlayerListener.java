package me.aurel2108.freeze;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezePlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setTo(event.getFrom());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler	
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()) && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
		else if(Freeze.freezeAll == true && (event.getPlayer().hasPermission("freeze.never") == false || event.getPlayer().isOp() == false))
		{
			event.setCancelled(true);
		}
	}

}
