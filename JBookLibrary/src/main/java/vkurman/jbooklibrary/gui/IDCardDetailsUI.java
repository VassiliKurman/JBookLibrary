package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.IDCardDetailsPanel;

/**
 * Dialog with details about IDCard.
 * 
 * <p>Date created: 2013.07.28
 * 
 *
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardDetailsUI extends JDialog {

	private static final long serialVersionUID = -3629755861480374887L;
	private IDCard idCard;
	
	/**
	 * Counstructor.
	 * 
	 * @param idCard
	 */
	public IDCardDetailsUI(IDCard idCard) {
		this.idCard = idCard;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Details of "+idCard.getUserName()+" IDCard: " + idCard.getCardID());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(10, 10));
		
		add(new IDCardDetailsPanel(idCard), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}