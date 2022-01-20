package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.tooling;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;

public class Application {
	
	public static void main(String [] args) {
		Cockpit cockpit = new Cockpit();
		System.out.println("An instance of my team player: " + cockpit);
		System.out.println("When called, it returns some JSON: " + cockpit.nextRound(""));
	}
}
