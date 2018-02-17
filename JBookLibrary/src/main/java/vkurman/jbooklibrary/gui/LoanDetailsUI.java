package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * All data about specified loan is available on this UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class LoanDetailsUI extends JDialog {
	private static final long serialVersionUID = 3727068740301365185L;
	private Loan loan;
	private JPanel contentPanel;
	
	/**
	 * Default Constructor.
	 * 
	 * @param loan
	 */
	public LoanDetailsUI(Loan loan) {
		this.loan = loan;
		
		showUI();
	}
	
	/**
	 * Setting UI details.
	 */
	private void showUI(){
		setTitle("Details of loan " + loan.getLoanID());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(10, 10));
		
		contentPanel.add(this.getLoanDetailsPanel(), BorderLayout.CENTER);
		contentPanel.add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		setContentPane(contentPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getLoanDetailsPanel() {
		JPanel panel = new JPanel(new SpringLayout());
		
		panel.add(new JLabel("Loan ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(loan.getLoanID())));
		panel.add(new JLabel("Book ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(loan.getBookID())));
		panel.add(new JLabel("Book Title:", JLabel.TRAILING));
		panel.add(new JTextField(loan.getBookTitle(), 50));
		panel.add(new JLabel("User ID:", JLabel.TRAILING));
		panel.add(new JTextField(Long.toString(loan.getUserID())));
		panel.add(new JLabel("User Name:", JLabel.TRAILING));
		panel.add(new JTextField(loan.getUserName()));
		panel.add(new JLabel("Borrow date:", JLabel.TRAILING));
		panel.add(new JTextField(BasicLibraryDateFormatter.formatDate(loan.getBorrowDate())));
		panel.add(new JLabel("Due back:", JLabel.TRAILING));
		panel.add(new JTextField(BasicLibraryDateFormatter.formatDate(loan.getDueDate())));
		panel.add(new JLabel("Item returned:", JLabel.TRAILING));
		panel.add(new JTextField((loan.getReturnDate() == null) ?
				"" : BasicLibraryDateFormatter.formatDate(loan.getReturnDate())));
		panel.add(new JLabel("Loan Status:", JLabel.TRAILING));
		panel.add(new JTextField(loan.getStatus().toString()));
		panel.add(new JLabel("Overdue days:", JLabel.TRAILING));
		panel.add(new JTextField(Integer.toString(loan.getOverdueDays())));
		
		SpringUtilities.makeCompactGrid(
				panel,
				10, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
}