package net.spoothie.lespace;


import java.util.HashMap;

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
	
	HashMap<SpoutPlayer, Boolean> hasFlag = new HashMap<SpoutPlayer, Boolean>();
	HashMap<SpoutPlayer, Integer> inTeam = new HashMap<SpoutPlayer, Integer>();

	@Override
	public void onEnable() {
		EventListener listener = new EventListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		skyManager = SpoutManager.getSkyManager();
		soundManager = SpoutManager.getSoundManager();
		
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
	
	private void joinLobby(SpoutPlayer player) {
		new Lobby(player);
	}
	
	private void joinSpectator(SpoutPlayer player) {
		player.getMainScreen().closePopup();
		player.setGameMode(GameMode.CREATIVE);
		player.setFlying(true);
		player.teleport(new Location(player.getWorld(), 0, 0, 0));
	}

	private void joinGame(SpoutPlayer player, Team team) {
		player.getMainScreen().closePopup();
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		team.add(player);
		player.teleport(new Location(team.getSpawn()));
		
		for(Player p : getServer().getOnlinePlayers())
			p.showPlayer(player);
	}
	
}
