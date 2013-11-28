package net.digiex.minemarket;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Mine Market 1.0 Copyright (C) 2013 xzKinGzxBuRnzx
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class MineMarket extends JavaPlugin {

	private static WorldGuard WorldGuard;
	private Logger log;

	private void registerEvents(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	@Override
	public void onDisable() {
		log.info("is now disabled!");
	}

	@Override
	public void onEnable() {
		log = getLogger();
		registerEvents(new MarketListener());
		getWorldGuard();
		log.info("is now enabled!");
	}

	public static boolean canModifyBlockHere(Block block) {
		return (WorldGuard == null) ? true : WorldGuard
				.canModifyBlockHere(block.getLocation());
	}

	void getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if (plugin == null
				|| !(plugin instanceof com.sk89q.worldguard.bukkit.WorldGuardPlugin)) {
			return;
		}
		WorldGuard = new WorldGuard(
				(com.sk89q.worldguard.bukkit.WorldGuardPlugin) plugin);
	}

	class MarketListener implements Listener {

		@EventHandler
		public void onBlockPlace(BlockPlaceEvent event) {
			Player player = event.getPlayer();
			Block block = event.getBlock();
			if (player.isOp() || player.hasPermission("minemarket.modify")) {
				return;
			}
			if (!canModifyBlockHere(block)) {
				switch (block.getType()) {
				case CHEST:
					return;
				case WALL_SIGN:
					return;
				default:
					player.sendMessage("This is a market area. You can only place / break (if allowed) chests and signs here...");
					event.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onBlockBreak(BlockBreakEvent event) {
			Player player = event.getPlayer();
			Block block = event.getBlock();
			if (player.isOp() || player.hasPermission("minemarket.modify")) {
				return;
			}
			if (!canModifyBlockHere(block)) {
				switch (block.getType()) {
				case CHEST:
					return;
				case WALL_SIGN:
					return;
				default:
					player.sendMessage("This is a market area. You can only place / break (if allowed) chests and signs here...");
					event.setCancelled(true);
				}
			}
		}
	}
}
