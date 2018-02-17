package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminFines;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Fine;
import vkurman.jbooklibrary.core.Loan;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.FineDetailsUI;
import vkurman.jbooklibrary.gui.LoanDetailsUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.filters.AssociationFilterPanel;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * Loans panel to display loans in the table.
 * 
 * <p><code>displayMode</code> is used to display new loans
 * for current session. If this mode is <code>rue</code> than
 * it means that data is not retrieved from database.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class LoansPanel extends JPanel implements FilterObserver {

	private static final long serialVersionUID = -5127522083023747240L;
	private User user;
	private Book book;
	private Loan loan;
	private GeneralStatus generalStatus;
	private StatusFilterPanel statusFilterPanel;
	private AssociationFilterPanel associationFilterPanel;
	private JTable table;
	private TitledBorder titledBorder;
	private JButton viewLoanButton,
		renewLoanButton,
		returnBookButton,
		viewFineButton;
	private LoansTableModel model;
	private boolean displayMode;
	
	/**
	 * Constructor that has main purpose only to display
	 * loans in the table. Panel is created using this constructor,
	 * than only borrowed list of loans from current session is
	 * displayed.
	 * 
	 * @param list
	 */
	public LoansPanel(List<Loan> list){
		displayMode = true;
		
		model = new LoansTableModel(list);
		
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Books borrowed",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		showUI(false, false);
	}
	
	public LoansPanel() {
		this(false, false, GeneralStatus.ACTIVE, null, null);
	}
	
	public LoansPanel(boolean showStatusFilters) {
		this(showStatusFilters, false, GeneralStatus.ACTIVE, null, null);
	}
	
	public LoansPanel(boolean showStatusFilters, boolean showAssociationFilters) {
		this(showStatusFilters, showAssociationFilters, GeneralStatus.ACTIVE, null, null);
	}
	
	public LoansPanel(boolean showStatusFilters, GeneralStatus generalStatus, User user) {
		this(showStatusFilters, false, generalStatus, user, null);
	}
	
	public LoansPanel(boolean showStatusFilters, GeneralStatus generalStatus, Book book) {
		this(showStatusFilters, false, generalStatus, null, book);
	}
	
	public LoansPanel(GeneralStatus generalStatus, User user, Book book){
		this(false, false, generalStatus, user, book);
	}
	
	public LoansPanel(
			boolean showStatusFilters,
			boolean showAssociationFilters,
			GeneralStatus generalStatus,
			User user,
			Book book){
		
		displayMode = false;
		
		this.user = user;
		this.book = book;
		this.loan = null;
		this.generalStatus = generalStatus;
		model = new LoansTableModel(generalStatus, user, book);
		
		// Creating StatusFilterPanel and registering itself in it
		statusFilterPanel = new StatusFilterPanel(generalStatus);
		statusFilterPanel.register(this);
		
		// Creating BookBasicSearchPanel and registering itself in it
		associationFilterPanel = new AssociationFilterPanel(true, true);
		associationFilterPanel.register(this);
		
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Loans: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		showUI(showStatusFilters, showAssociationFilters);
	}
	
	private void showUI(boolean showStatusFilters, boolean showAssociationFilters){
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);
		
		if(showStatusFilters || showAssociationFilters){
			add(getFilterPane(showStatusFilters, showAssociationFilters), BorderLayout.PAGE_START);
		}
		add(getTablePane(), BorderLayout.CENTER);
		if(!displayMode){
			add(getActionButtonPane(), BorderLayout.PAGE_END);
		}
	}
	
	private JScrollPane getTablePane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set fine from selected row
					loan = ((Loan) model.getLoan(table.getSelectedRow()));
					viewLoanButton.setEnabled(true);
					if(loan.isActive()){
						renewLoanButton.setEnabled(true);
						returnBookButton.setEnabled(true);
					} else {
						renewLoanButton.setEnabled(false);
						returnBookButton.setEnabled(false);
						if(loan.getDueDate().before((Calendar) loan.getReturnDate())){
							viewFineButton.setEnabled(true);
						} else {
							viewFineButton.setEnabled(false);
						}
					}
				} else {
					viewLoanButton.setEnabled(false);
					renewLoanButton.setEnabled(false);
					returnBookButton.setEnabled(false);
					viewFineButton.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(210);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(50);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private JPanel getActionButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		viewLoanButton = new JButton("View Loan");
		viewLoanButton.setActionCommand("viewLoan");
		viewLoanButton.setEnabled(false);
		viewLoanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							new LoanDetailsUI(loan);
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									LoansPanel.this,
									"Can't display LoanDetailsUI!");
						}
					}
				});
			}
		});
		
		renewLoanButton = new JButton("Renew Loan");
		renewLoanButton.setActionCommand("renewLoan");
		renewLoanButton.setEnabled(false);
		renewLoanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if(AdminBooks.getInstance().renewLoan(loan)){
								UIDisplayManager.displayInformationMessage(
										LoansPanel.this,
										"Loan for book "+loan.getBookTitle()+" renewed!");
								
								refreshTable();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									LoansPanel.this,
									"Can't renew loan!");
						}
					}
				});
			}
		});
		
		returnBookButton = new JButton("Return Book");
		returnBookButton.setActionCommand("returnBook");
		returnBookButton.setEnabled(false);
		returnBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if(AdminBooks.getInstance().returnBook(loan)){
								UIDisplayManager.displayInformationMessage(
										LoansPanel.this,
										"Book "+loan.getBookTitle()+" returned!");
								
								refreshTable();
							}
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									LoansPanel.this,
									"Can't return book!");
						}
					}
				});
			}
		});
		
		viewFineButton = new JButton("View Fine");
		viewFineButton.setActionCommand("viewFine");
		viewFineButton.setEnabled(false);
		viewFineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				List<Fine> fines = AdminFines.getInstance().getAllLoanFines(loan.getLoanID());
				final Fine fine;
				if(!fines.isEmpty()){
					fine = fines.get(0);
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								new FineDetailsUI(fine);
							} catch (Exception e) {
								UIDisplayManager.displayErrorMessage(
										LoansPanel.this,
										"Can't display FineDetailsUI!");
							}
						}
					});
				} else {
					UIDisplayManager.displayErrorMessage(
							LoansPanel.this,
							"Can't find Fine details in Database!");
				}
			}
		});
		
		panel.add(viewLoanButton);
		panel.add(renewLoanButton);
		panel.add(returnBookButton);
		panel.add(viewFineButton);
		
		return panel;
	}
	
	/**
	 * Creates and returns filter panel.
	 * 
	 * @param showStatusFilters
	 * @param showAssociationFilter
	 * @return JPanel
	 */
	private JPanel getFilterPane(boolean showStatusFilters, boolean showAssociationFilter){
		JPanel panel = new JPanel(new SpringLayout());
		
		if(showStatusFilters){
			panel.add(statusFilterPanel);
		}
		if(showAssociationFilter){
			panel.add(associationFilterPanel);
		}
		
		SpringUtilities.makeCompactGrid(panel, 1, panel.getComponentCount(), 5, 5, 5, 5);
		
		return panel;
	}
	
	/**
	 * Adds new loan to the table. This method needs to be used
	 * ONLY in <code>displayMode</code>.
	 * 
	 * @param loan
	 */
	public void addLoan(Loan loan){
		model.addLoan(loan);
	}
	
	/**
	 * Refreshes model and updates title text on the border.
	 */
	public void refreshTable(){
		model.replaceData(generalStatus, user, book);
		
		updateTitledBorder();
	}
	
	@Override
	public void update(String className, Object arg) {
		if(className.equals("AssociationFilterPanel")){
			if(((String) arg).equals("none")){
				user = null;
				book = null;
				refreshTable();
			} else if(((String) arg).equals("user")){
				book = null;
				refreshTable();
			} else if(((String) arg).equals("book")){
				user = null;
				refreshTable();
			}
		} else if(className.equals("BookBasicSearchPanel")){
			book = (Book) arg;
		} else if(className.equals("UserBasicSearchPanel")){
			user = (User) arg;
		} else if(className.equals("StatusFilterPanel")){
			generalStatus = (GeneralStatus) arg;
		}
		model.replaceData(generalStatus, user, book);
		
		updateTitledBorder();
	}
	
	/**
	 * Updates title text on the border.
	 */
	private void updateTitledBorder(){
		titledBorder.setTitle("Loans: "+model.getRowCount()+" records found");
		repaint();
	}
}