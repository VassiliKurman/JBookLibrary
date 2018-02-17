package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Dialog that displays details about specified <code>Reservation</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ReservationDetailsUI extends JDialog {

	private static final long serialVersionUID = 3727068740301365185L;
	private Reservation reservation;
	
	/**
	 * Constructor.
	 * 
	 * @param reservation
	 */
	public ReservationDetailsUI(Reservation reservation) {
		this.reservation = reservation;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Details of reservation " + reservation.getReservationID());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setLayout(new BorderLayout(10, 10));
		
		add(getDetailsPanel(), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with details about reservation.
	 * 
	 * @return JPanel
	 */
	private JPanel getDetailsPanel() {
		JPanel panel = new JPanel(new SpringLayout());
		
		panel.add(new JLabel("Reservation ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(reservation.getReservationID())));
		panel.add(new JLabel("Book ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(reservation.getBookID())));
		panel.add(new JLabel("Book Title:", JLabel.TRAILING));
		panel.add(new JTextField(reservation.getBookTitle(), 36));
		panel.add(new JLabel("User ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(reservation.getUserID())));
		panel.add(new JLabel("User Name:", JLabel.TRAILING));
		panel.add(new JTextField(reservation.getUserName()));
		panel.add(new JLabel("Reserved On:", JLabel.TRAILING));
		panel.add(new JTextField(BasicLibraryDateFormatter.formatDate(reservation.getReserveDate())));
		panel.add(new JLabel("Reservation expires:", JLabel.TRAILING));
		panel.add(new JTextField(BasicLibraryDateFormatter.formatDate(reservation.getExpireDate())));
		panel.add(new JLabel("Status:", JLabel.TRAILING));
		panel.add(new JTextField(reservation.getGeneralStatus().toString()));
		
		SpringUtilities.makeCompactGrid(
				panel,
				8, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
}