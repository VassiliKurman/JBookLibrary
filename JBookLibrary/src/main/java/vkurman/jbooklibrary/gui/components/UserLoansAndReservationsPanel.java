package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * Panel that displays user loans and reservations.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserLoansAndReservationsPanel extends JPanel{
	
	private static final long serialVersionUID = 6867146355137259409L;
	private LoansPanel loansPanel;
	private ReservationsPanel reservationsPanel;
	
	public UserLoansAndReservationsPanel(User user) {
		loansPanel = new LoansPanel(true, GeneralStatus.ACTIVE, user);
		reservationsPanel = new ReservationsPanel(true, GeneralStatus.ACTIVE, user);
		
		showUI();
	}

	private void showUI(){
		setPreferredSize(new Dimension(800, 500));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		add(getDataPanel(), BorderLayout.CENTER);
	}
	
	private JPanel getDataPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 5, 5));
		
		panel.add(loansPanel);
		panel.add(reservationsPanel);
		
		return panel;
	}
}