package net.spoothie.lespace.gui;

import net.spoothie.lespace.LeSpace;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;

public class Lobby extends GenericPopup {

	public Lobby(LeSpace plugin) {
		
		//Play-Button
		GenericButton play = new GenericButton("Play");
		play.setX(152).setY(5);
		play.setWidth(48).setHeight(12);
		
		//Spectate-Button
		GenericButton spectate = new GenericButton("Spectate");
		spectate.setX(227).setY(5);
		spectate.setWidth(48).setHeight(12);
		
		/*
		Container container = new GenericContainer();
		container.addChildren(play, spectate);
		container.setLayout(ContainerType.HORIZONTAL);
		container.setWidth(0).setHeight(0);
		*/
		
		this.attachWidgets(plugin, play, spectate);
		this.setTransparent(true);
	}
	
}
