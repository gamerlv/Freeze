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
		if(Freeze.toFreeze.contains(event.getPlayer().getName()))
		{
			event.setTo(event.getFrom());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if ( event.getPlayer() == null ) return; //if the fire was already in the world or was placed by the server (fireSpread) this is null
		if(Freeze.toFreeze.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler	
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(Freeze.toFreeze.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}

}
