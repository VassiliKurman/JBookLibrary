package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminRequests;
import vkurman.jbooklibrary.core.Request;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.gui.RequestHandlerUI;
import vkurman.jbooklibrary.gui.filters.ItemTypeFilterPanel;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

/**
 * This JPanel contains table with requests, buttons to
 * perform actions on selected request and filters to
 * show only chosen subset of requests.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class RequestsPanel extends JPanel implements FilterObserver {
	
	/**
	 * Auto generated serialVersionUID
	 */
	private static final long serialVersionUID = 6516132125099951138L;
	private Request request;
	private ItemType itemType;
	private GeneralStatus generalStatus;
	private ItemTypeFilterPanel itemTypeFilterPanel;
	private StatusFilterPanel statusFilterPanel;
	private JTable table;
	private JButton receivedButton;
	private JButton viewRequestButton;
	private JButton cancelRequestButton;
	private RequestsTableModel model;
	private TitledBorder titledBorder;
	
	/**
	 * Default Constructor. Displays active requests for books without
	 * status filter on the panel.
	 */
	public RequestsPanel() {
		this(false, false, ItemType.BOOK, GeneralStatus.ACTIVE);
	}
	
	/**
	 * Constructor that displays active requests for books.
	 */
	public RequestsPanel(boolean showFilters) {
		this(showFilters, showFilters, ItemType.BOOK, GeneralStatus.ACTIVE);
	}
	
	/**
	 * Constructor that displays requests for book.
	 */
	public RequestsPanel(boolean showFilters, GeneralStatus generalStatus) {
		this(showFilters, showFilters, ItemType.BOOK, generalStatus);
	}
	
	/**
	 * Constructor that displays active requests.
	 */
	public RequestsPanel(boolean showFilters, ItemType itemType) {
		this(showFilters, showFilters, itemType, GeneralStatus.ACTIVE);
	}
	
	/**
	 * Constructor that does not displays status filter panel.
	 */
	public RequestsPanel(ItemType itemType, GeneralStatus generalStatus) {
		this(false, false, itemType, GeneralStatus.ACTIVE);
	}
	
	/**
	 * Constructor.
	 */
	public RequestsPanel(
			boolean showItemTypesFilter,
			boolean showStatusFilter,
			ItemType itemType,
			GeneralStatus generalStatus){
		request = null;
		this.itemType = itemType;
		this.generalStatus = generalStatus;
		model = new RequestsTableModel(itemType, generalStatus);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Requests: "+model.getRowCount()+" records found",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null);
		
		// Initialising ItemTypeFilterPanel and registering itself in it
		itemTypeFilterPanel = new ItemTypeFilterPanel();
		itemTypeFilterPanel.register(this);
		
		// Creating StatusFilterPanel and registering itself in it
		statusFilterPanel = new StatusFilterPanel(generalStatus);
		statusFilterPanel.register(this);
		
		showUI(showItemTypesFilter, showStatusFilter);
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param showStatusFilters
	 */
	private void showUI(boolean showItemTypesFilter, boolean showStatusFilters){
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);
		
		if(showItemTypesFilter || showStatusFilters){
			add(getFilterPane(showItemTypesFilter, showStatusFilters), BorderLayout.PAGE_START);
		}
		add(getTablePane(), BorderLayout.CENTER);
		add(getActionButtonPane(), BorderLayout.PAGE_END);
	}
	
	/**
	 * JScrollPane with table of requests.
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getTablePane() {
		table = new JTable(model);
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(table.getSelectedRow() > -1) {
					// Set fine from selected row
					request = (Request) model.getRequest(table.getSelectedRow());
					
					// Setting button visibility 
					if(request.getStatus() == GeneralStatus.ACTIVE){
						receivedButton.setEnabled(true);
						cancelRequestButton.setEnabled(true);
					} else {
						receivedButton.setEnabled(false);
						cancelRequestButton.setEnabled(false);
					}
					viewRequestButton.setEnabled(true);
				} else {
					request = null;
					receivedButton.setEnabled(false);
					viewRequestButton.setEnabled(false);
					cancelRequestButton.setEnabled(false);
				}
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(200);
		table.getColumnModel().getColumn(9).setPreferredWidth(50);
        table.getColumnModel().getColumn(10).setPreferredWidth(50);
        table.getColumnModel().getColumn(11).setPreferredWidth(50);
        table.getColumnModel().getColumn(12).setPreferredWidth(50);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);
		
		return scrollPane;
	}
	
	/**
	 * Creates and returns panel with buttons to perform tasks
	 * on selected request.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionButtonPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		receivedButton = new JButton("Process Request");
		receivedButton.setToolTipText("Processes received items!");
		receivedButton.setEnabled(false);
		receivedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if(AdminRequests.getInstance().received(request)){
					// Displaying confirmation dialog
					JOptionPane.showMessageDialog(null,
							"Request updated!",
							"Request Processed",
							JOptionPane.INFORMATION_MESSAGE);
					
					model.replaceData(itemType, generalStatus);
				}
			}
		});
		
		viewRequestButton = new JButton("View Request");
		viewRequestButton.setEnabled(false);
		viewRequestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						RequestHandlerUI dialog = new RequestHandlerUI(request);
						if(dialog.isOkPressed()){
							model.replaceData(itemType, generalStatus);
						}
					}
				});
			}
		});
		
		cancelRequestButton = new JButton("Cancel Request");
		cancelRequestButton.setEnabled(false);
		cancelRequestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if(AdminRequests.getInstance().remove(request)){
					// Displaying confirmation dialog
					JOptionPane.showMessageDialog(null,
							"Request cancelled!",
							"Request Cancelation Message",
							JOptionPane.INFORMATION_MESSAGE);
					
					model.replaceData(itemType, generalStatus);
				}
			}
		});
		
		// Adding buttons to panel
		panel.add(viewRequestButton);
		panel.add(receivedButton);
		panel.add(cancelRequestButton);
		
		return panel;
	}
	
	/**
	 * Displays filters. Filter can be enabled or disabled via
	 * boolean parameters.
	 * 
	 * @param showItemTypesFilter
	 * @param showStatusFilter
	 * @return JPanel
	 */
	private JPanel getFilterPane(boolean showItemTypesFilter, boolean showStatusFilter){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		if(showItemTypesFilter){
			panel.add(itemTypeFilterPanel);
		}
		if(showStatusFilter){
			panel.add(statusFilterPanel);
		}
		
		return panel;
	}
	
	/**
	 * Adds request to the table.
	 * 
	 * @param request
	 */
	public void addRequest(Request request){
		if(request != null){
			model.addRequest(request);
		}
	}
	
	/**
	 * Removes specified request from table.
	 * 
	 * @param request
	 */
	public void removeRequest(Request request){
		if(request != null) {
			model.removeRequest(request);
		}
	}
	
	/**
	 * Asking table model to update it's data passing current
	 * item type and request status as parameters.
	 */
	public void refreshTable(){
		// Updating table data
		model.replaceData(itemType, generalStatus);
		
		// Updating titled border
		updateTitledBorder();
	}
	
	/**
	 * This method requests to update data in table model.
	 */
	@Override
	public void update(String className, Object arg) {
		if(className.equals("ItemTypeFilterPanel")){
				itemType = (ItemType) arg;
		} else if(className.equals("StatusFilterPanel")){
				generalStatus = (GeneralStatus) arg;
		}
		
		// Updating table model
		model.replaceData(itemType, generalStatus);
		
		// Updating titled border
		updateTitledBorder();
	}
	
	/**
	 * This method updates title in titled border.
	 */
	private void updateTitledBorder(){
		// Setting title for titled border
		titledBorder.setTitle(
				"Requests: "+model.getRowCount()+" records found");
		
		// Updating UI
		repaint();
	}
	
	/**
	 * Table model for requests table.
	 * 
	 * <p>Date created: 2013.07.28
	 * 
	 * @author Vassili Kurman
	 * @version 0.1
	 */
	private class RequestsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6247398227594467843L;
		private String[] columnNames = {
				"ID",
				"Item Type",
				"ISBN",
				"Title",
				"Issue",
				"Year",
				"Author",
				"Publisher",
				"Comments",
				"Quantity",
				"Request Date",
				"Cancelation Date",
				"Status"};
		private List<Request> requests;
		
		/**
		 * Constructor.
		 * 
		 * @param itemType
		 * @param generalStatus
		 */
		public RequestsTableModel(ItemType itemType, GeneralStatus generalStatus) {
			requests = new ArrayList<Request>();
			
			retrieveData(itemType, generalStatus);
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		@Override
		public int getRowCount() {
			return requests.size();
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			Request request = requests.get(row);
			switch(col){
				case 0:
					return request.getID();
				case 1:
					return request.getType();
				case 2:
					return request.getIsbn();
				case 3:
					return request.getTitle();
				case 4:
					return request.getIssue();
				case 5:
					return request.getYear();
				case 6:
					return request.getAuthor();
				case 7:
					return request.getPublisher();
				case 8:
					return request.getComments();
				case 9:
					return request.getQuantity();
				case 10:
					return BasicLibraryDateFormatter.formatDate(request.getRequestDate());
				case 11:
					if(request.getCancelationDate() != null){
						return BasicLibraryDateFormatter.formatDate(request.getCancelationDate());
					} else {
						return null;
					}
				case 12:
					return request.getStatus().toString();
				default:
					return null;
			}
		}
		
		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		@Override
		public Class<?> getColumnClass(int c) {
			return (getValueAt(0, c) == null) ? String.class : getValueAt(0, c).getClass();
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
		
		/**
		 * Adds request to the list of requests.
		 * 
		 * @param request
		 */
		public void addRequest(Request request){
			if(request != null){
				int size = requests.size();
				this.requests.add(request);
				this.fireTableRowsInserted(size, size);
			}
		}
		
		/**
		 * Returns request from specified row.
		 * 
		 * @param row
		 * @return Request
		 */
		public Request getRequest(int row){
			return requests.get(row);
		}
		
		/**
		 * Removes specified request from the list.
		 * 
		 * @param request
		 */
		public void removeRequest(Request request){
			if(request != null){
				if (requests.contains(request)) {
					int index = requests.indexOf(request);
					this.requests.remove(request);
					this.fireTableRowsDeleted(index, index);
				}
			}
		}
		
		/**
		 * Retrieves data from database with specified parameters.
		 * 
		 * @param itemType
		 * @param generalStatus
		 */
		private void retrieveData(ItemType itemType, GeneralStatus generalStatus){
			requests = AdminRequests.getInstance().getRequests(generalStatus, itemType);
		}
		
		/**
		 * Replaces data in the table. Call this method if table needs to be refreshed.
		 * 
		 * @param itemType
		 * @param generalStatus
		 */
		public void replaceData(ItemType itemType, GeneralStatus generalStatus){
			this.requests.clear();
			this.retrieveData(itemType, generalStatus);
			this.fireTableDataChanged();
		}
	}
}