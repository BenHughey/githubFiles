package Memory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game {
	public static void main (String[]args) {
		//Instantiates object of the Board class
		Board gui = new Board();

		gui.setSize(1000, 800);
		gui.setBackground(Color.BLACK);
		
		//Calls boards displayCards method to place cards on table
		gui.displayCards();
		
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
}