package net.spoothie.lespace;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class EventListener implements Listener {

	private LeSpace plugin;
	private World world;
	private Location flag;
	private Location base1;
	private Location base2;
	
	public EventListener(LeSpace plugin) {
		this.plugin = plugin;
		world = plugin.getServer().getWorld("world");
		flag = new Location(world, 1187, 170, 599);
		base1 = new Location(world, 1175, 173, 611);
		base2 = new Location(world, 1199, 173, 587);
	}
	
	
	@EventHandler
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
		SpoutPlayer player = event.getPlayer();
		
		plugin.joinLobby(player);
		
		plugin.skyManager.setCloudsVisible(player, false);
		plugin.skyManager.setMoonVisible(player, false);
		plugin.skyManager.setSunVisible(player, false);
		plugin.skyManager.setStarsVisible(player, false);
		
		player.setGravityMultiplier(0.7);
		player.setWalkingMultiplier(1.5);
		player.setJumpingMultiplier(1.5);
		player.setSkin("http://www.minecraftskins.info/atlas.png");
		player.setTitle("");
		player.setFoodLevel(0);
		
		plugin.hasFlag.put(player, false);
		plugin.inTeam.put(player, 1);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		
		if(event.getPlayer().getLocation().distance(flag) < 1) {
			if(!plugin.hasFlag.containsValue(true)) {
				plugin.hasFlag.put(player, true);
				player.getWorld().getBlockAt(flag).setType(Material.AIR);
				player.setCape("http://dl.dropbox.com/u/33328672/kc.png");
				player.setTitle(ChatColor.RED + "Flag");
				player.sendMessage("You got the Flag!");
			}
		}
		else if(plugin.hasFlag.get(player) == true) {
			if((player.getLocation().distance(base1) < 1 && plugin.inTeam.get(player) == 1) || (player.getLocation().distance(base2) < 1 && plugin.inTeam.get(player) == 2)) {
				plugin.hasFlag.put(player, false);
				world.getBlockAt(flag).setType(Material.STEP);
				
				//for(Player p : plugin.getServer().getOnlinePlayers()) 
				//	((SpoutPlayer)p).spawnTextEntity("Flag", flag.add(0.5, 1.5, 0.5), 1, Integer.MAX_VALUE, new Vector());
				
				player.resetCape();
				player.setTitle("");
				player.sendMessage("You saved the flag");
			}
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if(event.toWeatherState() == true)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onThunderChange(ThunderChangeEvent event) {
		if(event.toThunderState() == true)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		event.getPlayer().setSprinting(false);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.STARVATION)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntityType() == EntityType.SNOWBALL)
			event.setDamage(2);
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 0);
	}
	
	@EventHandler
	public void onButtonClick(ButtonClickEvent event) {
		SpoutPlayer player = event.getPlayer();
		
		if(event.getButton().getText() == "Spectate") {
			plugin.joinSpectator(player);
		}
		else if(event.getButton().getText() == "Play") {
			//Attach team choice to player's screen
			//plugin.joinGame(player, team);
			player.setGameMode(GameMode.SURVIVAL);
		}
		else if(event.getButton().getText() == "Return") {
			plugin.joinLobby(player);
		}
	}

}
