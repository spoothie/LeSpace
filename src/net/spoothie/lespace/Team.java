package net.spoothie.lespace;

import java.util.ArrayList;

import org.bukkit.Location;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Team {
	
	private Location spawn;
	private ArrayList<SpoutPlayer> players = new ArrayList<SpoutPlayer>();
	
	public Team() {
		
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public SpoutPlayer[] getPlayers() {
		return (SpoutPlayer[]) players.toArray();
	}
	
	public boolean hasPlayer(SpoutPlayer player) {
		if(players.contains(player))
			return true;
		
		return false;
	}
	
	public void addPlayer(SpoutPlayer player) {
		players.add(player);
	}
	
	public void removePlayer(SpoutPlayer player) {
		players.remove(player);
	}
}
