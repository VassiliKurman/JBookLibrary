package vkurman.jbooklibrary.gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JOptionPane;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.db.DBArchitector;

/**
 * <code>UIDisplayManager</code> is a class that is responsible
 * for displaying some message dialogs as well, as all the user
 * interfaces for all object administrators, such as books
 * administrator, users administrator, etc.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UIDisplayManager {
	
	/**
	 * Displays error message in dialog box.
	 * 
	 * @param parent
	 * @param message
	 */
	public static void displayErrorMessage(Component parent, String message){
		JOptionPane.showMessageDialog(
				parent,
				message,
				AdminPrefs.LIBRARY_NAME,
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays information message in dialog box.
	 * 
	 * @param parent
	 * @param message
	 */
	public static void displayInformationMessage(Component parent, String message){
		JOptionPane.showMessageDialog(
				parent,
				message,
				AdminPrefs.LIBRARY_NAME,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays information message in dialog box. Dialog is displayed
	 * through <code>EventQueue</code> class.
	 * 
	 * @param parent
	 * @param message
	 */
	public static void displayDBErrorMessage(Component parent, String message){
		int response = JOptionPane.showConfirmDialog(
				parent,
				message,
				AdminPrefs.LIBRARY_NAME,
				JOptionPane.OK_CANCEL_OPTION);
		
		if(response == JOptionPane.OK_OPTION) DBArchitector.getInstance().createTables();;
	}
	
	/**
	 * Displays Users Administrator UI
	 */
	public static void startUsersAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new UserAdminUI();
				}
			});	
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Users Administration UI Exception!");
		}
	}
	
	/**
	 * Displays IDCards Administrator UI
	 */
	public static void startIDCardsAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new IDCardAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Can't display IDCards Administration UI");
		}
	}
	
	/**
	 * Displays Books Administrator UI
	 */
	public static void startBooksAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new BookAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Can't display Books Administration UI");
		}
	}
	
	/**
	 * Displays Loans Administrator UI
	 */
	public static void startLoansAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new LoanAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Loans Administration UI Exception!");
		}
	}
	
	/**
	 * Displays Reservations Administrator UI. 
	 */
	public static void startReservationsAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new ReservationsAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Reservations Administration UI Exception!");
		}
	}
	
	/**
	 * Displays Requests Administrator UI
	 */
	public static void startRequestsAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new RequestsAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Can't display Requests Administration UI");
		}
	}
	
	/**
	 * Displays Fines Administrator UI
	 */
	public static void startFinesAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new FinesAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Can't display Fines Administration UI");
		}
	}
	
	/**
	 * Displays Payments Administrator UI
	 */
	public static void startPaymentsAdminUI() {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new PaymentsAdminUI();
				}
			});
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(
					null,
					"Can't display Payments Administration UI");
		}
	}
}