package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminIDCards;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.core.TransObject;

/**
 * Dialog to search and display IDCards.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class IDCardFinderUI extends JDialog {

	private static final long serialVersionUID = 4254542534658843016L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private boolean okPressed, idCardFound;
	private TransObject obj;
	private JTable table;
	private Object[][] idCardsToDisplay;
	private List<IDCard> foundIDCards;
	
	/**
	 * Constructor.
	 * 
	 * @param obj
	 * @param title
	 */
	public IDCardFinderUI(TransObject obj, String title) {
		this.obj = obj;
		this.idCardFound = false;
		this.foundIDCards = new ArrayList<IDCard>();
		
		showUI(title);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param obj
	 * @param title
	 * @param idCardNumber
	 */
	public IDCardFinderUI(TransObject obj, String title, int idCardNumber) {
		this.obj = obj;
		this.foundIDCards = new ArrayList<IDCard>();
		if (this.findIDCard(idCardNumber)){
			this.idCardFound = true;
			
			showUI(title);
		} else {
			this.idCardFound = false;
			
			UIDisplayManager.displayErrorMessage(
					IDCardFinderUI.this,
					"Can't find IDCard number: " + idCardNumber);
			
			showUI(title);
		}
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param title
	 */
	private void showUI(String title) {
		this.okPressed = false;
		setTitle(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getDataPane(), BorderLayout.CENTER);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(this.getButtonPane(), BorderLayout.PAGE_END);
	}
	
	private JPanel getButtonPane(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					okPressed = true;
					dispose();
				}
			});
			
			// Control okButton visibility
			if(table.getSelectedRow() > -1) {
				okButton.setEnabled(true);
				getRootPane().setDefaultButton(okButton);
			} else {
				okButton.setEnabled(false);
				getRootPane().setDefaultButton(cancelButton);
			}
			
			buttonPane.add(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					okPressed = false;
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
		return buttonPane;
	}
	
	private JScrollPane getDataPane() {
		String[] columnNames = {"IDCard", "User ID", "Name"};
		
		// Find IDCards
		this.getData();
		
		table = new JTable(idCardsToDisplay, columnNames);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set idCardObject to selected member
					obj.setObject((IDCard) idCardsToDisplay[table.getSelectedRow()][3]);
					
					okButton.setEnabled(true);
					getRootPane().setDefaultButton(okButton);
				} else {
					okButton.setEnabled(false);
					getRootPane().setDefaultButton(cancelButton);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private void getData() {
		int total = 0;
		Iterator<IDCard> iter;
		
		if (!idCardFound) {
			List<IDCard> l = AdminIDCards.getInstance().getAllActiveIDCards();
			total = l.size();
			iter = l.iterator();
		} else {
			total = foundIDCards.size();
			iter = foundIDCards.iterator();
		}
		
		idCardsToDisplay = new Object[total][4];
		for (int i = 0; i < total; i++) {
			IDCard c = iter.next();
			idCardsToDisplay[i][0] = c.getCardID();
			idCardsToDisplay[i][1] = c.getUserID();
			idCardsToDisplay[i][2] = c.getUserName();
			idCardsToDisplay[i][3] = c;
		}
	}
	
	private boolean findIDCard(int idCardNumb) {
		boolean found = false;
		Iterator<IDCard> iter = AdminIDCards.getInstance().getAllActiveIDCards().iterator();
		// Checking if list is empty or not
		if (!foundIDCards.isEmpty()) {
			foundIDCards.clear();
		}
		
		while(iter.hasNext()) {
			IDCard c = iter.next();
			if(idCardNumb == c.getCardID()) {
				foundIDCards.add(c);
				found = true;
			}
		}
		return found;
	}
	
	public boolean isOKPressed() {
		return this.okPressed;
	}
}