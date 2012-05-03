package net.spoothie.lespace;

import java.util.ArrayList;

import org.getspout.spoutapi.player.SpoutPlayer;

public class Game {

	private ArrayList<Team> teams = new ArrayList<Team>();
	
	public Game() {
		
	}
	
	public void start() {
		for(Team team : teams) {
			for(SpoutPlayer player : team.getPlayers()) {
				player.teleport(team.getSpawn());
			}
		}
	}
	
	public void addTeam(Team team) {
		teams.add(team);
	}
	
}
