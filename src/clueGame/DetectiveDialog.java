package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Detective Dialog -- custom dialog class for the detective notes window,
 * where the player can monitor the cards they've seen and their current guess
 * @author Hannah Lee
 * @author Savannah Paul
 *
 */

@SuppressWarnings("serial")
public class DetectiveDialog extends JDialog{
	
	/**
	 * DetectiveDialog constructor that creates and formats the Detective Notes window and panels
	 */
	public DetectiveDialog() {
		setTitle("Detective Notes");
		setSize(600, 600);
		
		JPanel leftPanel = new JPanel(); 
		leftPanel.setLayout(new GridLayout(3,1));
		
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		peoplePanel.setLayout(new GridLayout(3,0));
		JCheckBox person1 = new JCheckBox("Trashy Tracy");
		peoplePanel.add(person1);
		JCheckBox person2 = new JCheckBox("Batty Betty");
		peoplePanel.add(person2);
		JCheckBox person3 = new JCheckBox("Sinful Syndie");
		peoplePanel.add(person3);
		JCheckBox person4 = new JCheckBox("Wacky Wendy");
		peoplePanel.add(person4);
		JCheckBox person5 = new JCheckBox("Deluxe Dolly");
		peoplePanel.add(person5);
		JCheckBox person6 = new JCheckBox("Lusty Lucy");
		peoplePanel.add(person6);
		leftPanel.add(peoplePanel);
		
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weaponsPanel.setLayout(new GridLayout(3,0));
		JCheckBox weapon1 = new JCheckBox("Frozen Turkey");
		weaponsPanel.add(weapon1);
		JCheckBox weapon2 = new JCheckBox("Rusted Spoon");
		weaponsPanel.add(weapon2);
		JCheckBox weapon3 = new JCheckBox("Broken Champagne Glass");
		weaponsPanel.add(weapon3);
		JCheckBox weapon4 = new JCheckBox("XXXtra Strength Laxatives");
		weaponsPanel.add(weapon4);
		JCheckBox weapon5 = new JCheckBox("Massage Wand");
		weaponsPanel.add(weapon5);
		JCheckBox weapon6 = new JCheckBox("Frayed Extension Cord");
		weaponsPanel.add(weapon6);
		leftPanel.add(weaponsPanel);
		
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		roomsPanel.setLayout(new GridLayout(3,0));
		JCheckBox room1 = new JCheckBox("Green Room");
		roomsPanel.add(room1);
		JCheckBox room2 = new JCheckBox("Morgue");
		roomsPanel.add(room2);
		JCheckBox room3 = new JCheckBox("Craft Space");
		roomsPanel.add(room3);
		JCheckBox room4 = new JCheckBox("Exercise Room");
		roomsPanel.add(room4);
		JCheckBox room5 = new JCheckBox("Kitchen");
		roomsPanel.add(room5);
		JCheckBox room6 = new JCheckBox("Pool");
		roomsPanel.add(room6);
		JCheckBox room7 = new JCheckBox("Lounge");
		roomsPanel.add(room7);
		JCheckBox room8 = new JCheckBox("Dining Room");
		roomsPanel.add(room8);
		JCheckBox room9 = new JCheckBox("Torture Room");
		roomsPanel.add(room9);
		leftPanel.add(roomsPanel);
		add(leftPanel, BorderLayout.WEST);
		
		
		JPanel rightPanel = new JPanel(); 
		rightPanel.setLayout(new GridLayout(3,1));
		
		JPanel peopleGuessPanel = new JPanel();
		peopleGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		peopleGuessPanel.setLayout(new GridLayout(1,0));
		String[] peopleChoices = {"Trashy Tracy", "Batty Betty", "Sinful Syndie", "Wacky Wendy", "Deluxe Dolly", "Lusty Lucy", "Unsure"};
		JComboBox<String> ppl = new JComboBox<String>(peopleChoices);
		peopleGuessPanel.add(ppl);
		rightPanel.add(peopleGuessPanel);
		
		JPanel weaponsGuessPanel = new JPanel();
		weaponsGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		weaponsGuessPanel.setLayout(new GridLayout(1,0));
		String[] weaponsChoices = {"Frozen Turkey", "Rusted Spoon", "Broke Champagne Glass", "XXXtra Strength Laxatives", "Massage Wand", "Frayed Extension Cord", "Unsure"};
		JComboBox<String> wpn = new JComboBox<String>(weaponsChoices);
		weaponsGuessPanel.add(wpn);
		rightPanel.add(weaponsGuessPanel);
		
		JPanel roomsGuessPanel = new JPanel();
		roomsGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		roomsGuessPanel.setLayout(new GridLayout(1,0));
		String[] roomsChoices = {"Green Room", "Morgue", "Craft Space", "Exercise Room", "Kitchen", "Pool", "Lounge", "Dining Room", "Torture Room", "Unsure"};
		JComboBox<String> room = new JComboBox<String>(roomsChoices);
		roomsGuessPanel.add(room);
		rightPanel.add(roomsGuessPanel);
		
		add(rightPanel, BorderLayout.EAST);
	}
}
