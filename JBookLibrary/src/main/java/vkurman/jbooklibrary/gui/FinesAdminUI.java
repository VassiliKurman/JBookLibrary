package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.FinesPanel;

/**
 * Fines administrator.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class FinesAdminUI extends JDialog {
	
	private static final long serialVersionUID = -2054558827146754695L;
	private FinesPanel finesPanel;
	
	/**
	 * Constructor.
	 */
	public FinesAdminUI() {
		finesPanel = new FinesPanel(
				true,
				true,
				GeneralStatus.ACTIVE,
				0L,
				0L);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Fines Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(finesPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}