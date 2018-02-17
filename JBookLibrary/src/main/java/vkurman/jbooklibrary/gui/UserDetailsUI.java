package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.UserDetailsPanel;
import vkurman.jbooklibrary.gui.components.UserFinesAndPaymentsPanel;
import vkurman.jbooklibrary.gui.components.UserLoansAndReservationsPanel;

/**
 * User Interface that displays <code>User</code>
 * details on the <code>JDialog</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserDetailsUI extends JDialog {
	
	private static final long serialVersionUID = 4367389386025342717L;
	private UserDetailsPanel userDetailsPanel;
	private UserLoansAndReservationsPanel userLoansAndReservationsPanel;
	private UserFinesAndPaymentsPanel userFinesAndPaymentsPanel;
	private User user;
	
	/**
	 * Constructor.
	 * 
	 * @param u
	 */
	public UserDetailsUI(User u) {
		this.user = u;
		userDetailsPanel = new UserDetailsPanel(user);
		userLoansAndReservationsPanel = new UserLoansAndReservationsPanel(user);
		userFinesAndPaymentsPanel = new UserFinesAndPaymentsPanel(user);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	public void showUI(){
		setTitle("User Details: " + user.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		JPanel content = new JPanel(new FlowLayout());
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.add(getTabbedPanel());
		
		add(content, BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with user details and user
	 * activities.
	 * 
	 * @return JTabbedPane
	 */
	private JTabbedPane getTabbedPanel(){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Personal Details", userDetailsPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab("Loans & Reservations", userLoansAndReservationsPanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		tabbedPane.addTab("Fines & Payments", userFinesAndPaymentsPanel);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		return tabbedPane;
	}
}