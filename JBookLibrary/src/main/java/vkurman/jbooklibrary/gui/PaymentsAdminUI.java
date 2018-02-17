package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.PaymentsPanel;

/**
 * Payments administrator UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PaymentsAdminUI extends JDialog {
	
	private static final long serialVersionUID = -2054558827146754695L;
	private PaymentsPanel paymentsPanel;
	
	/**
	 * Constructor.
	 */
	public PaymentsAdminUI() {
		paymentsPanel = new PaymentsPanel(true);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Payments Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLayout(new BorderLayout(10, 10));
		
		getContentPane().add(paymentsPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}