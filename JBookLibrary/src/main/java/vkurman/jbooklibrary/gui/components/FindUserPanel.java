package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;




import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.UserFinderUI;

/**
 * Panel with textfield and button to find user
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class FindUserPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8748396726037803573L;
	private User user;
	private boolean isUserSet, isBorrowerSelected;
	private JLabel lblUser;
	private JTextField txtUser;
	private JButton findUserButton;
	private JRadioButton borrowerButton, librarianButton;
	private String borrowerString, librarianString;
	
	/**
	 * Default constructor.
	 */
	public FindUserPanel() {
		this.isUserSet = false;
		this.borrowerString = "Borrower";
		this.librarianString = "Librarian";
		this.showUI();
	}
	
	/**
	 * Create the panel.
	 */
	private void showUI() {
		setLayout(new BorderLayout(5, 5));
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(310, 74));
		
		lblUser = new JLabel("User:");
		
		txtUser = new JTextField();
		txtUser.setColumns(16);
		
		findUserButton = new JButton("Find User");
		findUserButton.setActionCommand("findUser");
		findUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				findUser();
			}
		});
		
		//Create RadioButton Panel
		JPanel rButtonPanel = new JPanel();
		rButtonPanel.setLayout(new FlowLayout());
		{
			//Create the radio buttons.
		    borrowerButton = new JRadioButton(borrowerString);
		    borrowerButton.setMnemonic(KeyEvent.VK_B);
		    borrowerButton.setActionCommand(borrowerString);
		    borrowerButton.setSelected(true);

		    librarianButton = new JRadioButton(librarianString);
		    librarianButton.setMnemonic(KeyEvent.VK_L);
		    librarianButton.setActionCommand(librarianString);

		    //Group the radio buttons.
		    ButtonGroup group = new ButtonGroup();
		    group.add(borrowerButton);
		    group.add(librarianButton);
		    
		    //Register a listener for the radio buttons.
		    borrowerButton.addActionListener(this);
		    librarianButton.addActionListener(this);
		    
		    //Add buttons to panel
		    rButtonPanel.add(borrowerButton);
		    rButtonPanel.add(librarianButton);
		}
		
		//Add components to panel
		this.add(lblUser, BorderLayout.LINE_START);
		this.add(txtUser, BorderLayout.CENTER);
		this.add(findUserButton, BorderLayout.LINE_END);
		this.add(rButtonPanel, BorderLayout.PAGE_END);
	}
	
	private void findUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject u = new TransObject();
					
					UserFinderUI dialog;
					if (txtUser.getText().isEmpty()) {
						dialog = new UserFinderUI(u);
					} else {
						dialog = new UserFinderUI(u, txtUser.getText());
					}
					
					if(dialog.isOKPressed()){
						// Checking if user has IDCard to prevent creating second card for same user
						user =  (User) u.getObject();
						if(!user.hasIDCard()) {
							txtUser.setEditable(false);
							txtUser.setText(user.getName());
							isUserSet = true;
						} else {
							UIDisplayManager.displayErrorMessage(
									FindUserPanel.this,
									"User already has IDCard! \n"
									+ "Please de-activate existing card first!!!");
						}
					} else {
						txtUser.setEditable(true);
						isUserSet = false;
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							FindUserPanel.this,
							"Can't display UserFinderUI!");
				}
			}
		});
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isUserSet() {
		return isUserSet;
	}

	public void setUserSet(boolean isUserSet) {
		this.isUserSet = isUserSet;
	}

	public boolean isBorrower() {
		return isBorrowerSelected;
	}

	public void setBorrower(boolean isBorrower) {
		this.isBorrowerSelected = isBorrower;
	}

	public String getTxtUser() {
		return txtUser.getText();
	}

	public void setTxtUser(String txtUser) {
		this.txtUser.setText(txtUser);
	}
	
	public void setEditableTxtUser(boolean b) {
		this.txtUser.setEditable(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(borrowerString)) {
	        this.isBorrowerSelected = true;
	    } else {
	        this.isBorrowerSelected = false;
	    }
	}
}