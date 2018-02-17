package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * Panel that displays user fines and payments.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserFinesAndPaymentsPanel extends JPanel{
	
	private static final long serialVersionUID = -1253397245433604013L;
	private FinesPanel finesPanel;
	private PaymentsUpdateControl control;
	private PaymentsPanel paymentsPanel;
	
	public UserFinesAndPaymentsPanel(User user) {
		finesPanel = new FinesPanel(
				true,
				false,
				GeneralStatus.ACTIVE,
				0L,
				user.getUserID());
		paymentsPanel = new PaymentsPanel(user);
		control = new PaymentsUpdateControl();
		
		finesPanel.setControl(control);
		control.setFines(finesPanel);
		control.setPayments(paymentsPanel);
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
		
		panel.add(finesPanel);
		panel.add(paymentsPanel);
		
		return panel;
	}
}