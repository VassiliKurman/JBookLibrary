package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.IDCardsPanel;

/**
 * IDCards administrator class.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardAdminUI extends JDialog {
	
	private static final long serialVersionUID = -4814035842561448922L;
	private JButton activateButton;
	private IDCardsPanel idCardsPanel;
	
	/**
	 * Constructor.
	 */
	public IDCardAdminUI() {
		idCardsPanel = new IDCardsPanel(true);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("IDCards Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		getContentPane().setLayout(new BorderLayout(10, 10));
		getContentPane().add(this.getActivationButtonPanel(), BorderLayout.PAGE_START);
		getContentPane().add(idCardsPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with button to activate new
	 * <code>IDCard</code>
	 * 
	 * @return JPanel
	 */
	private JPanel getActivationButtonPanel() {
		JPanel pane = new JPanel();
		
		activateButton = new JButton("Activate New IDCard");
		activateButton.setActionCommand("Activate");
		activateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							IDCardActivationUI dialog = new IDCardActivationUI();
							if(dialog.isOkPressed()){
								// Adding user to users panel
								idCardsPanel.addIDCard(dialog.getIDCard());
							}
							
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									IDCardAdminUI.this,
									"Can't display IDCardActivationUI!");
						}
					}
				});
			}
		});
		pane.add(activateButton);
		
		return pane;
	}
}