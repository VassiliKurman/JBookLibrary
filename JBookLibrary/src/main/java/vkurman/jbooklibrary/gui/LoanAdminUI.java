package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vkurman.jbooklibrary.enums.Action;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.LoansPanel;

/**
 * Loans administrator UI..
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class LoanAdminUI extends JDialog {

	private static final long serialVersionUID = 7497510763724839670L;
	private LoansPanel loansPanel;
	
	/**
	 * Constructor
	 */
	public LoanAdminUI() {
		loansPanel = new LoansPanel(
				true,
				true,
				GeneralStatus.ACTIVE,
				null,
				null);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Loan Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(5, 5));
		getContentPane().add(getActionsButtonPanel(), BorderLayout.PAGE_START);
		getContentPane().add(loansPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with button to borrow books.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionsButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton newButton = new JButton("New Loan");
		newButton.setActionCommand("New");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BooksHandlerUI dialog = new BooksHandlerUI(Action.BORROW);
							if(!dialog.isShowing()){
								loansPanel.refreshTable();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									LoanAdminUI.this,
									"Error occured while trying to display dialog box!");
						}
					}
				});
			}
		});
		buttonPane.add(newButton);
		
		return buttonPane;
	}
}