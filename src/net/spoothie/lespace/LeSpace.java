package net.spoothie.lespace;


import java.util.HashMap;

import net.spoothie.lespace.gui.Lobby;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.SoundManager;

public class LeSpace extends JavaPlugin {

	public SkyManager skyManager;
	public SoundManager soundManager;
	public HashMap<SpoutPlayer, Boolean> hasFlag = new HashMap<SpoutPlayer, Boolean>();
	public HashMap<SpoutPlayer, Integer> inTeam = new HashMap<SpoutPlayer, Integer>();
	private Location lobby;

	@Override
	public void onEnable() {
		EventListener listener = new EventListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		skyManager = SpoutManager.getSkyManager();
		soundManager = SpoutManager.getSoundManager();
		lobby = getServer().getWorld("world").getSpawnLocation();
		
		setEnvironment(getServer().getWorld("world"));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void setEnvironment(final World world) {
		world.setStorm(false);
		world.setThundering(false);

		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
		    public void run() {
		    	world.setTime(16000);
		    }
		}, 0L, 20L);
	}
	
	public void joinLobby(SpoutPlayer player) {
		player.setGameMode(GameMode.CREATIVE);
		player.setFlying(true);
		player.teleport(lobby);
		player.getMainScreen().attachPopupScreen(new Lobby(this));
		
		for(Player p : getServer().getOnlinePlayers())
			p.hidePlayer(player);
	}
	
	public void joinSpectator(SpoutPlayer player) {
		player.getMainScreen().closePopup();
		player.setGameMode(GameMode.CREATIVE);
		player.setFlying(true);
		player.teleport(lobby);
		
		for(Player p : getServer().getOnlinePlayers())
			p.hidePlayer(player);
	}

	public void joinGame(SpoutPlayer player, Team team) {
		player.getMainScreen().closePopup();
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		hideGameOverlay(player);
		team.addPlayer(player);
		//player.teleport(team.getSpawn());
		
		for(Player p : getServer().getOnlinePlayers())
			p.showPlayer(player);
	}
	
	public void hideGameOverlay(SpoutPlayer player) {
		player.getMainScreen().getHealthBar().setVisible(false).setDirty(false);
		player.getMainScreen().getArmorBar().setVisible(false).setDirty(false);
		player.getMainScreen().getExpBar().setVisible(false).setDirty(false);
		player.getMainScreen().getHungerBar().setVisible(false).setDirty(false);
		player.getMainScreen().updateWidget(player.getMainScreen().getHealthBar());
	}
}
