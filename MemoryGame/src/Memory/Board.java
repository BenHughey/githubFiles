package Memory;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

//Board class inherits from JFrame 
public class Board extends JFrame{
	
	HashMap<String, Card> cardset1 = new HashMap();//Hashmap of all cards
	private int clicks = 0;	//For keeping only 2 cards turned over at a time
	private int tries = 0; //Keeps track of user tries
	private int paired = 0; //Game ends when this reaches 10
	private Card firstSelected = null; //For tracking first selected card
	private Card chosenCard = null;	//card that was clicked
	File folder = new File("src/memoryGamePics/");//Create list of files in image directory
    
    File[] files = folder.listFiles();
	
    public void displayCards() {
	
        ArrayList<String> listFiles = new ArrayList<String>();// used to shuffle cards
        
        try {
        	//Sets grid layout
    	    this.setLayout(new GridLayout(5, 4));
        	
    	    for (File file : files){
        	
    	    	System.out.println(file.getName());
    	    	//Creates a Card for each file & sets iconName as the file name
    	    	Card cardx = new Card();
    	    	cardx.setIconName(file.getName().toString());
    	    	System.out.println(file.toString());
    	    	cardx.addActionListener(new SelectListener());
    	    	//Adds each Card to cardset1 hashmap with the fileName as its key
    	    	cardset1.put(file.getName(), cardx); 
    	    	//Adds File names to listFiles ArrayList
    	    	listFiles.add(file.getName());
            	}
        	}catch(Exception e) {
        		System.out.println(e);
        	}
        	//Shuffles ArrayList containing File names so the matching cards are added to board at random
        	Collections.shuffle(listFiles);
        	for (String key : listFiles) {
        	   System.out.println("key: " + key + " value: " + cardset1.get(key));
        	   this.add(cardset1.get(key));
        	}
        	System.out.println("files : " + cardset1.size());
    	}
    	//Inner class for button click event
		class SelectListener implements ActionListener { 
			public void actionPerformed(ActionEvent event) {
			
				System.out.println(clicks);
				clicks ++;
				
					//won't let event execute if 2 cards are already turned
					if (clicks > 2  ) {
						return;
					
				}
			Object source = event.getSource();	//gets source of event caller
			//loops through cardset1 cards and gives the one that matches the source the appropriate image value
			for (Card newCard : cardset1.values()) {
				
				if (newCard == source ) {

					chosenCard = newCard;
					String clickedCardIconName = chosenCard.getIconName();
					//exits method if matched cards are clicked again
					if (chosenCard.getMatched() == true) {
						clicks = 0;
						if (firstSelected.getMatched() == false){
							firstSelected.setIcon(null);
						}
						return;
					}
					
						System.out.println(clickedCardIconName);
					
						try {
						
							Image pic2;
							pic2 = ImageIO.read(getClass().getResource("/memoryGamePics/" +clickedCardIconName));
							pic2 = pic2.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
							chosenCard.setIcon(new ImageIcon(pic2));
						
						} catch (IOException e) {
							e.printStackTrace();
						} 
						//Saves first clicked card to compare to next clicked
						if (clicks <= 1) {
						firstSelected = chosenCard;
						}
					}
			}
			
			
				if (clicks >=2) {
							System.out.println(firstSelected.getIconName());
							//Checks for match by checking the icon name is not identical but contains the same first 4 letters
							if (firstSelected.getIconName() != chosenCard.getIconName() 
									&& firstSelected.getIconName().contains(chosenCard.getIconName().substring(0,4))) {
								System.out.println("Match");
								//breaks out of method if cards already matched
								if (chosenCard.getMatched() == true) {
									clicks = 0;
									return;
								}
								chosenCard.setMatched(true);
								firstSelected.setMatched(true);
								
								tries ++;
								paired ++;
								System.out.println("Paired:" + paired);
								clicks = 0;
								//When all cards are matched a message box will appear stating score and program will close
								if (paired == 10) {
									JOptionPane.showMessageDialog(null, "Congratulations! "
									+ "\n You did it in " + tries + " tries");
									//Only doing this to demonstrate how to destroy an array
									files = null;
									System.exit(0);
								}
							}
							//Runs if cards don't match
							else {	

								TimerTask task;
								Timer timer = new Timer();
								//Resets Cards after .65 second delay
								task = new TimerTask() {
										public void run() { 
											chosenCard.setIcon(null);
											firstSelected.setIcon(null);
											clicks = 0;
							            	System.out.println("clicks restarted");
							            	tries ++;
							            	System.out.println("Tries: " + tries);
										}
							          };
							       timer.schedule(task, 650);
								}
					}
			
			}
	}
}

	