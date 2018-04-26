package clueGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;

/**
 * ControlGUI -- this is the class that holds all of the GUI Code. It creates a 
 * window from which the game can be played. 
 * @author Hannah Lee
 * @author Savannah Paul
 *
 */

public class ControlGUI extends JFrame{
	private static ControlGUI clueGame; 
	private static Board board; 

	private static JTextField currentName;
	private static JTextField rollField;
	private static JTextField guessField; 
	private static JTextField responseField; 
	private static JButton nextPlayer;
	private DetectiveDialog dialog;
	static int count;

	private static JComboBox<String> guessPerson;
	private static JComboBox<String> guessWeapon;
	private static JComboBox<String> guessRoom;

	private static JComboBox<String> accusePerson;
	private static JComboBox<String> accuseWeapon;
	private static JComboBox<String> accuseRoom;

	private static Dialog guessDialog;
	private static Dialog accusationDialog;

	private static Boolean gameWon;


	public static void main (String[] args) throws IOException, BadConfigFormatException {
		clueGame = new ControlGUI();
		clueGame.setVisible(true);
		JOptionPane.showMessageDialog(clueGame, "You are "+ board.getPlayers().get(0).getName() + ", press Next Player to start!", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * ControlGUI -- constructor that sets the size to (1000,180) and calls all the functions
	 * that create the various game parts
	 * @throws IOException 
	 */
	public ControlGUI() throws IOException {
		setTitle("Clue Game");
		setSize(1000, 775); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = Board.getInstance(); 
		board.initialize();
		createPlayerHandDisplay(board.getPlayers().get(0));
		createButtonPanel(); 
		createLabelPanel();
		add(board, BorderLayout.CENTER);

		gameWon = false; 
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		dialog = new DetectiveDialog();
		
		createGuessDialog();
		createAccusationDialog(); 
		count = 0;
	}

	/**
	 * createButtonPanel -- creates the next player and make accusation buttons, also exhibits
	 * whose turn it is. 
	 */
	private void createButtonPanel() {
		// Creating BUttons for next player and making accusation
		nextPlayer = new JButton ("Next Player"); 
		JButton makeAccusation = new JButton ("Make Accusation");

		// Creating whose turn and name panels	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Whose Turn?"));
		panel.setLayout(new GridLayout(2,0));

		JLabel currentPlayer = new JLabel("Name: "); 
		currentName = new JTextField(); 
		currentName.setEditable(false);		

		panel.add(currentPlayer); 
		panel.add(currentName); 

		// adding button and panel to north panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(0, 3));

		northPanel.add(panel); 
		northPanel.add(nextPlayer);
		northPanel.add(makeAccusation); 

		// ACTION LISTENER CODE -------------------------------------------

		// For clicking next Player
		add(northPanel, BorderLayout.NORTH);  		
		class nextPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(!board.getTurnOver() && count != 0){
					JOptionPane.showMessageDialog(clueGame, "You need to finish your turn.", "Game Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					board.playerTurn();
					count++;
				}
			}

		}
		nextPlayer.addActionListener(new nextPlayerListener());

		class accusationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (!board.getTurnOver() && board.getCurrentPlayerIndex() == 0) {
					accusationDialog.setVisible(true);
				}
				else if (board.getTurnOver() && board.getCurrentPlayerIndex() == 0){
					JOptionPane.showMessageDialog(clueGame, "Your turn is over.", "Game Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(clueGame, "You can only accuse on your turn.", "Game Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		}
		makeAccusation.addActionListener(new accusationListener());


	}


	/**
	 * createLabelPanel -- creates a labels and borders for the roll, guess, and
	 * response parts of the game. 
	 */
	private void createLabelPanel() {
		JPanel southPanel = new JPanel(); 
		southPanel.setLayout(new GridLayout(0,3));

		// Creating Roll and Die panel -------
		JLabel roll = new JLabel("Roll: ");
		rollField = new JTextField(2); 
		rollField.setEditable(false);

		JPanel rollPanel = new JPanel();
		rollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));

		rollPanel.add(roll); 
		rollPanel.add(rollField);
		southPanel.add(rollPanel);

		// Creating Guess Panel ---------
		JLabel guess = new JLabel("Guess: ");
		guessField = new JTextField(20);
		guessField.setEditable(false);

		JPanel guessPanel = new JPanel(); 
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		guessPanel.add(guess); 
		guessPanel.add(guessField);
		southPanel.add(guessPanel);

		// Creating Response and Guess Result Panel ------
		JLabel response = new JLabel("Response: "); 		
		responseField = new JTextField(20);
		responseField.setEditable(false);	

		JPanel responsePanel = new JPanel();
		responsePanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		responsePanel.add(response);
		responsePanel.add(responseField);
		southPanel.add(responsePanel);

		// adding southPanel to GUI
		add(southPanel, BorderLayout.SOUTH);
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createDetectiveNotes());
		return menu;
	}

	/**
	 * Creates File exit Item
	 * @return
	 */
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	/**
	 * Creates detective notes drop down window
	 * @return
	 */
	private JMenuItem createDetectiveNotes() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	/**
	 * create's players hand display on right side of board.
	 * Separates by card type 
	 * @param hp human player
	 */
	private void createPlayerHandDisplay(Player hp) {
		// Panel components - composed of a grid of size 1
		JPanel personCards = new JPanel();
		personCards.setLayout(new GridLayout(0,1));
		personCards.setBorder(new TitledBorder(new EtchedBorder(), "People"));

		JPanel roomCards = new JPanel();
		roomCards.setLayout(new GridLayout(0,1));
		roomCards.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));

		JPanel weaponCards = new JPanel();
		weaponCards.setLayout(new GridLayout(0,1));
		weaponCards.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		// populate labels for each respective card type
		for (Card c : hp.getHand()) {
			switch(c.getCardType()) {
			case PERSON:
				personCards.add(new JLabel(c.getCardName())); 
				break;
			case ROOM:
				roomCards.add(new JLabel(c.getCardName())); 
				break;
			case WEAPON:
				weaponCards.add(new JLabel(c.getCardName())); 
				break;
			default:
				break; 
			}
		}

		// Create the main
		JPanel handDisplayPanel = new JPanel();
		handDisplayPanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		handDisplayPanel.setLayout(new GridLayout(3,1));

		// add person, room, and weapon panels to hand panel
		handDisplayPanel.add(personCards);
		handDisplayPanel.add(roomCards);
		handDisplayPanel.add(weaponCards);

		// add hand panel
		add(handDisplayPanel, BorderLayout.EAST);
	}

	/**
	 * updates text field with the current player's name
	 * @param playerName
	 */
	public static void updateCurrentPlayer(String playerName) {
		currentName.setText(playerName);
	}

	/**
	 * updates text field with the newly rolled number
	 * @param rollNum
	 */
	public static void updateRoll(String rollNum) {
		rollField.setText(rollNum); 
	}

	public static void showInvalidLocationMessage() {
		JOptionPane.showMessageDialog(clueGame, "Invalid Location", "Error", JOptionPane.ERROR_MESSAGE);		
	}
	
//--------------------------
	
	public static void setGameWon(Boolean result){
		gameWon = result;
	}
	
	public static boolean getGameWon() {
		return gameWon;
	}

	public static void setGuess(Solution guess) {
		guessField.setText(guess.person + ", " + guess.room+ ", " + guess.weapon);
	}

	public static void setResponse(Card returned) {
		if (returned != null)
			responseField.setText(returned.getCardName());
		else
			responseField.setText("No response"); 
	}

	public static void displayGameWon() {
		JOptionPane.showMessageDialog(clueGame, board.getPlayers().get(board.currentPlayerIndex).getName() + " won the game!", "Clue Game", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}	

	public void createGuessDialog() {
		guessDialog = new Dialog(this, "Make a Guess", true);
		guessDialog.setSize(300, 200);
		guessDialog.setLayout(new GridLayout(0,2));
		
		
		guessDialog.add(new JLabel("Person"));
		guessPerson = new JComboBox<String>();
		for (Player p : board.getPlayers()) {
			guessPerson.addItem(p.getName());
		}
		guessDialog.add(guessPerson);
		
		guessDialog.add(new JLabel("Your Room"));
		guessRoom = new JComboBox<String>();
		guessDialog.add(guessRoom);
		
		guessDialog.add(new JLabel("Weapon"));
		guessWeapon = new JComboBox<String>();
		for (Card c : board.getWeaponCards()) {
			guessWeapon.addItem(c.getCardName());
		}
		guessDialog.add(guessWeapon);
		
		
		JButton submitButton = new JButton("Submit");
		class submitButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				Solution guess = new Solution(guessPerson.getSelectedItem().toString(), guessRoom.getSelectedItem().toString(), guessWeapon.getSelectedItem().toString());
				Card result = board.handleSuggestion(guess, board.getPlayers().get(board.getCurrentPlayerIndex()).getName(), null);
				if(result != null){
					for(Player p: board.getPlayers()){
						p.addToPossibleCards(result);
					}
				}
				setGuess(guess);
				setResponse(result);
				board.setTurnOver(true);
				guessDialog.dispose();
			}
			
		}
		submitButton.addActionListener(new submitButtonListener()); 
		guessDialog.add(submitButton);
		
		
		JButton cancelButton = new JButton("Cancel");
		class cancelButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				guessDialog.dispose();
			}
		}
		cancelButton.addActionListener(new cancelButtonListener());
		guessDialog.add(cancelButton);
	}
	
	public static void launchGuess() {
		guessRoom.removeAllItems();
		int playerRow = board.getPlayers().get(board.getCurrentPlayerIndex()).getRow();
		int playerCol = board.getPlayers().get(board.getCurrentPlayerIndex()).getCol();
		char roomInitial = board.getCellAt(playerRow, playerCol).getInitial();
		String room = board.getRooms().get(roomInitial); 
		guessRoom.addItem(room);
		guessDialog.setVisible(true);
	}
		
	public void createAccusationDialog() {
		accusationDialog = new Dialog(this, "Make an Accusation", true);
		accusationDialog.setSize(300, 200);
		accusationDialog.setLayout(new GridLayout(0,2));
		
		accusationDialog.add(new JLabel("Person"));
		accusePerson = new JComboBox<String>();
		for (Player p : board.getPlayers()) {
			accusePerson.addItem(p.getName()) ;
		}
		accusationDialog.add(accusePerson);
		
		accusationDialog.add(new JLabel("Room"));
		accuseRoom = new JComboBox<String>();
		for (Card c : board.getRoomCards()) {
			accuseRoom.addItem(c.getCardName());
		}
		accusationDialog.add(accuseRoom);
		
		accusationDialog.add(new JLabel("Weapon"));
		accuseWeapon = new JComboBox<String>();
		for (Card c : board.getWeaponCards()) {
			accuseWeapon.addItem(c.getCardName());
		}
		accusationDialog.add(accuseWeapon);
		
		JButton accuseButton = new JButton("Accuse");
		class accuseListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				Solution accusation = new Solution(); 
				accusation.person = accusePerson.getSelectedItem().toString();
				accusation.weapon = accuseWeapon.getSelectedItem().toString();
				accusation.room = accuseRoom.getSelectedItem().toString();
				boolean GuessCorrect = board.checkAccusation(accusation);
				if (GuessCorrect) {
					displayGameWon();
				}
				else {
					JOptionPane.showMessageDialog(clueGame, "Your accusation of " + accusePerson.getSelectedItem().toString() + ", "+ accuseRoom.getSelectedItem().toString() + ", " + accuseWeapon.getSelectedItem().toString() + " was incorrect.", "Game Message", JOptionPane.INFORMATION_MESSAGE);
				}
				board.setTurnOver(true);
				board.clearTargets();
				board.makeAccusation = true;
				accusationDialog.dispose();
			}

			
			
		}
		accuseButton.addActionListener(new accuseListener());
		accusationDialog.add(accuseButton);
		
		
		JButton cancelButton2 = new JButton("Cancel");
		class cancelButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				accusationDialog.dispose();
			}
		}
		cancelButton2.addActionListener(new cancelButtonListener());
		accusationDialog.add(cancelButton2);
	}
	
	public static void displayWrongAccusation(Solution accusation) {
		JOptionPane.showMessageDialog(clueGame, accusation.person + ", " + accusation.weapon + ", " + accusation.room + " is incorrect.", "Game Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static Board getBoard() {
		return board;
	}
	
}
