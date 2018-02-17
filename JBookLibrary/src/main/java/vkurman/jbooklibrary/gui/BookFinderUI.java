package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.gui.components.BooksTableModel;

/**
 * Dialog that displays found books in the table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookFinderUI extends JDialog {
	
	private static final long serialVersionUID = 6130956867850145083L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private boolean okPressed;
	private TransObject obj;
	private JTable table;
	private BooksTableModel model;
	private TitledBorder titledBorder;
	private List<Book> foundBooks;
	private boolean displayFilters;
	
	/**
	 * Constructor. Sets <code>title</code> to <code>null</code>,
	 * <code>ItemStatus</code> to <code>null</code> and
	 * <code>displayFilter</code> to <code>true</code>.
	 * 
	 * @param obj
	 */
	public BookFinderUI(TransObject obj) {
		this(obj, null, null, true);
	}
	
	/**
	 * Constructor. Sets <code>title</code> to <code>null</code> and
	 * <code>ItemStatus</code> to <code>null</code>.
	 * 
	 * @param obj
	 * @param displayFilters
	 */
	public BookFinderUI(TransObject obj, boolean displayFilters) {
		this(obj, null, null, displayFilters);
	}
	
	/**
	 * Constructor. Sets <code>title</code> to <code>null.
	 * 
	 * @param obj
	 * @param generalStatus
	 * @param displayFilters
	 */
	public BookFinderUI(TransObject obj, ItemStatus status, boolean displayFilters) {
		this(obj, null, status, displayFilters);
	}
	
	/**
	 * Constructor. Sets <code>title</code> to <code>null</code> and
	 * <code>displayFilter</code> to <code>false</code>.
	 * 
	 * @param obj
	 * @param generalStatus
	 */
	public BookFinderUI(TransObject obj, ItemStatus status) {
		this(obj, null, status, false);
	}
	
	public BookFinderUI(TransObject obj, String title, ItemStatus status, boolean displayFilters) {
		this.obj = obj;
		this.displayFilters = displayFilters;
		this.foundBooks = new ArrayList<Book>();
		
		if (findBook(title, status)) {
			model = new BooksTableModel(foundBooks);
		} else {
			model = new BooksTableModel(status, false);
			
			UIDisplayManager.displayErrorMessage(
					BookFinderUI.this,
					"Can't find Book with text in title: " + title);
		}
		
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Books: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI() {
		okPressed = false;
		
		setTitle("Book Finder");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));
		setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		
		contentPanel.setBorder(titledBorder);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getDataPane(), BorderLayout.CENTER);
		
		if(displayFilters){
			getContentPane().add(this.getFilterPane(), BorderLayout.PAGE_START);
		}
		add(contentPanel, BorderLayout.CENTER);
		add(this.getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getFilterPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"GeneralStatus Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Create the radio buttons.
	    JRadioButton allFilterButton = new JRadioButton("All");
	    allFilterButton.setMnemonic(KeyEvent.VK_A);
	    allFilterButton.setActionCommand("All");
	    allFilterButton.setSelected(true);
	    
	    JRadioButton disposedFilterButton = new JRadioButton("Disposed");
	    disposedFilterButton.setMnemonic(KeyEvent.VK_D);
	    disposedFilterButton.setActionCommand("Disposed");
	    
	    JRadioButton onLoanFilterButton = new JRadioButton("On Loan");
	    onLoanFilterButton.setMnemonic(KeyEvent.VK_L);
	    onLoanFilterButton.setActionCommand("On Loan");
	    
	    JRadioButton onShelfFilterButton = new JRadioButton("On Shelf");
	    onShelfFilterButton.setMnemonic(KeyEvent.VK_S);
	    onShelfFilterButton.setActionCommand("On Shelf");
	    
	    JRadioButton reservedFilterButton = new JRadioButton("Reserved");
	    reservedFilterButton.setMnemonic(KeyEvent.VK_R);
	    reservedFilterButton.setActionCommand("Reserved");
	    
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(allFilterButton);
	    group.add(disposedFilterButton);
	    group.add(onLoanFilterButton);
	    group.add(onShelfFilterButton);
	    group.add(reservedFilterButton);

	    //Register a listener for the radio buttons.
	    allFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				model.replaceData(null, false);
			}
		});
	    
	    disposedFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				model.replaceData(ItemStatus.DISPOSED, false);
			}
		});
		
		onLoanFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				model.replaceData(ItemStatus.ONLOAN, false);
			}
		});
		
		onShelfFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				model.replaceData(ItemStatus.ONSHELF, false);
			}
		});
		
		reservedFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				model.replaceData(ItemStatus.RESERVED, false);
			}
		});
		
		panel.add(allFilterButton);
		panel.add(onLoanFilterButton);
		panel.add(onShelfFilterButton);
		panel.add(reservedFilterButton);
		panel.add(disposedFilterButton);
		
		return panel;
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
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set userObject to selected member
					obj.setObject((Book) model.getBook(table.getSelectedRow()));
					
					okButton.setEnabled(true);
					getRootPane().setDefaultButton(okButton);
				} else {
					okButton.setEnabled(false);
					getRootPane().setDefaultButton(cancelButton);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	private boolean findBook(String title, ItemStatus status) {
		boolean found = false;
		// Checking if list is empty or not
		if (!foundBooks.isEmpty()) {
			foundBooks.clear();
		}
		
		Iterator<Book> iter = AdminBooks.getInstance().getBooks(status, false).iterator();
		
		while(iter.hasNext()) {
			Book b = iter.next();
			if (b != null) {
				if(title != null){
					// Converting to lower case to make search case insensitive
					if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
						foundBooks.add(b);
						found = true;
					}
				} else {
					foundBooks.add(b);
					found = true;
				}
			}
		}
		
		return found;
	}
	
	/**
	 * Confirms that <code>OK</code> button was pressed or not.
	 * 
	 * @return boolean
	 */
	public boolean isOKPressed() {
		return this.okPressed;
	}
}