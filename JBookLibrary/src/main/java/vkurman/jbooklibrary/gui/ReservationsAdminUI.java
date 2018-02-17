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

import vkurman.jbooklibrary.activityregister.ActivityRegister;
import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.enums.Action;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.ReservationsPanel;

/**
 * Reservations administrator UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ReservationsAdminUI extends JDialog {
	
	private static final long serialVersionUID = -3749617416150434197L;
	private ReservationsPanel reservationsPanel;
	
	/**
	 * Constructor.
	 */
	public ReservationsAdminUI() {
		// Starting to update reservation statuses
		ActivityRegister.newActivity(
				ReservationsAdminUI.this,
				"Requesting to update reservations statuses!");
		AdminReservations.getInstance().updateReservationsStatus();
		// Completing to update reservation statuses
		ActivityRegister.newActivity(
				ReservationsAdminUI.this,
				"Reservations statuses updated!");
		
		reservationsPanel = new ReservationsPanel(
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
		setTitle("Reservations Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(5, 5));
		getContentPane().add(getActionsButtonPanel(), BorderLayout.PAGE_START);
		getContentPane().add(reservationsPanel, BorderLayout.CENTER);
		getContentPane().add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with button to make book reservation.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionsButtonPanel(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton newButton = new JButton("New Reservation");
		newButton.setActionCommand("New");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BooksHandlerUI dialog = new BooksHandlerUI(Action.RESERVE);
							if(!dialog.isShowing()){
								reservationsPanel.refreshTable();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									ReservationsAdminUI.this,
									"Can't reserve book!");
						}
					}
				});
			}
		});
		buttonPane.add(newButton);
		
		return buttonPane;
	}
}