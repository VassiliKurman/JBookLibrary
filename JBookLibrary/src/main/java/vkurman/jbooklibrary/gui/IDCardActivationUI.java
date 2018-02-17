package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Dialog to activate new IDCard for user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardActivationUI extends JDialog {
	
	/**
	 * Auto-generated serialVersionUID
	 */
	private static final long serialVersionUID = -762124040455300127L;
	private User user;
	private IDCard idCard;
	private JButton cancelButton, okButton, findUserButton;
	private JTextField txtUser;
	
	private boolean userSet, okPressed;
	
	/**
	 * Constructor.
	 */
	public IDCardActivationUI() {
		this.userSet = false;
		this.okPressed = false;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Activate IDCard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		add(getMainPanel(), BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns main panel with field for input and
	 * search button.
	 * 
	 * @return JPanel
	 */
	private JPanel getMainPanel(){
		JPanel panel = new JPanel(new SpringLayout());
		
		txtUser = new JTextField(16);
		txtUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtUser.getText().length() == AdminPrefs.USER_ID_LENGTH){
					try{
						// Checking that text is number
						@SuppressWarnings("unused")
						long id = Long.parseLong(txtUser.getText());
						// If exception is not thrown, that find user by id entered
						findUser();
					} catch(NumberFormatException nfe){
						// Do nothing
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		findUserButton = new JButton("Find User");
		findUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				findUser();
			}
		});
		
		panel.add(new JLabel("User:", JLabel.TRAILING));
		panel.add(txtUser);
		panel.add(findUserButton);
		
		SpringUtilities.makeCompactGrid(
				panel,
				1, 3,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Performs input checks and user search actions.
	 */
	private void findUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject obj = new TransObject();
					UserFinderUI dialog = new UserFinderUI(obj, txtUser.getText());
					
					if(dialog.isOKPressed()){
						// Checking if user has IDCard to prevent creating second card for same user
						user = (User) obj.getObject();
						if (!user.hasIDCard()) {
							txtUser.setEditable(false);
							txtUser.setText(user.getName());
							userSet = true;
							okButton.setEnabled(true);
						} else {
							resetText();
							
							UIDisplayManager.displayErrorMessage(
									IDCardActivationUI.this,
									"User already has IDCard! \n"
									+ "Please de-activate existing card first!!!");
						}
					} else {
						resetText();
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							IDCardActivationUI.this,
							"Can't display UserFinderUI!");
				}
			}
		});
	}
	
	/**
	 * Creates and returns panel with <code>OK</code> and
	 * <code>Cancel</code> buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				IDCard card = AdminIDCards.getInstance().activateIDCard(user.getUserID());
				if(card != null){
					idCard = card;
					okPressed = true;
					
					UIDisplayManager.displayErrorMessage(
							IDCardActivationUI.this,
							idCard.toString());
					
					dispose();
				} else {
					UIDisplayManager.displayErrorMessage(
							IDCardActivationUI.this,
							"New IDCard for " + user.getName() + " NOT activated!");
				}
			}
		});
		if(isUserSet()){
			okButton.setEnabled(true);
			getRootPane().setDefaultButton(okButton);
		} else {
			okButton.setEnabled(false);
			getRootPane().setDefaultButton(cancelButton);
		}
		buttonPane.add(okButton);
		
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
		
		return buttonPane;
	}
	
	/**
	 * Resetting input text field.
	 */
	private void resetText(){
		txtUser.setEditable(true);
		txtUser.setText("");
		userSet = false;
		okButton.setEnabled(false);
	}
	
	/**
	 * Returns <code>true</code> if user has been set.
	 * 
	 * @return boolean
	 */
	private boolean isUserSet() {
		return userSet;
	}
	
	/**
	 * Returns <code>true</code> if "OK" button pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed(){
		return okPressed;
	}
	
	/**
	 * Returns IDCard.
	 * 
	 * @return IDCard
	 */
	public IDCard getIDCard(){
		return idCard;
	}
}