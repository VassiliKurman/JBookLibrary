package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Welcome dialog to get major inputs from user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class WelcomeUI extends JDialog {
	
	private static final long serialVersionUID = 3176923237309051466L;
	
	private boolean initialised;
	
	private JTextField txtLibraryName;
	private JTextField txtPath;
	private JTextField txtUser;
	private JTextField txtPass;
	
	/**
	 * Constructor.
	 */
	public WelcomeUI(){
		initialised = false;
		
		txtLibraryName = new JTextField(32);
		txtLibraryName.setText(AdminPrefs.LIBRARY_NAME);
		txtPath = new JTextField(AdminPrefs.DATABASE_PATH);
		txtUser = new JTextField(AdminPrefs.DATABASE_USER);
		txtPass = new JTextField(AdminPrefs.DATABASE_PASSWORD);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setResizable(false);
		setTitle("Welcome!");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		
		add(createContent(), BorderLayout.CENTER);
		add(createButtons(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns content panel.
	 * 
	 * @return JPanel
	 */
	private JPanel createContent(){
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		
		JPanel library = new JPanel(new SpringLayout());
		library.setBorder(BorderFactory.createTitledBorder(""));
		library.add(new JLabel("Library name:", JLabel.TRAILING));
		library.add(txtLibraryName);
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				library,
				1, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		
		JPanel db = new JPanel(new SpringLayout());
		db.setBorder(BorderFactory.createTitledBorder("Database"));
		db.add(new JLabel("Database path:", JLabel.TRAILING));
		db.add(txtPath);
		db.add(new JLabel("Username:", JLabel.TRAILING));
		db.add(txtUser);
		db.add(new JLabel("Password:", JLabel.TRAILING));
		db.add(txtPass);
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				db,
				3, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		JPanel comment = new JPanel(new FlowLayout(FlowLayout.CENTER));
		comment.setBorder(BorderFactory.createTitledBorder("Important Information"));
		comment.add(new JLabel("For a full list of setings please go to" +
				"\n\"Preferences\" under the \"Tools\" menu.",
				JLabel.CENTER));
		
		panel.add(library, BorderLayout.PAGE_START);
		panel.add(db, BorderLayout.CENTER);
		panel.add(comment, BorderLayout.PAGE_END);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel createButtons(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				initialise();
				dispose();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		
		panel.add(doneButton);
		panel.add(cancelButton);
		
		return panel;
	}
	
	/**
	 * Sets all essential properties and <code>initialised</code>
	 * variable to <code>true</code>.
	 */
	private void initialise(){
		AdminPrefs.setLibraryName(txtLibraryName.getText());
		AdminPrefs.setDatabasePath(txtPath.getText());
		AdminPrefs.setDatabaseUser(txtUser.getText());
		AdminPrefs.setDatabasePassword(txtPass.getText());
		
		initialised = true;
	}
	
	/**
	 * Returns <code>true</code> if <code>Done</code> button
	 * pressed.
	 * 
	 * @return boolean
	 */
	public boolean isInitialised(){
		return initialised;
	}
}