/*
 * Copyright 2018 Vassili Kurman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.Reservation;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.BookDetailsUI;
import vkurman.jbooklibrary.gui.ReservationDetailsUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.UserDetailsUI;
import vkurman.jbooklibrary.gui.filters.AssociationFilterPanel;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * This JPanel contains table with reservations, buttons to perform actions on
 * selected reservation and filters to show only chosen subset of reservations.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ReservationsPanel extends JPanel implements FilterObserver {
	private static final long serialVersionUID = 2067798285955451522L;
	private User user;
	private Book book;
	private Reservation reservation;
	private GeneralStatus generalStatus;
	private StatusFilterPanel statusFilterPanel;
	private AssociationFilterPanel associationFilterPanel;
	private JTable table;
	private JButton cancelReservationButton;
	private JButton viewBookButton;
	private JButton viewUserButton;
	private JButton viewReservationButton;
	private ReservationsTableModel model;
	private TitledBorder titledBorder;

	/**
	 * Default Constructor.
	 */
	public ReservationsPanel() {
		this(false, false, GeneralStatus.ACTIVE, null, null);
	}

	/**
	 * Constructor.
	 */
	public ReservationsPanel(boolean showStatusFilters,
			GeneralStatus generalStatus, User user) {
		this(showStatusFilters, false, generalStatus, user, null);
	}

	/**
	 * Constructor.
	 */
	public ReservationsPanel(boolean showStatusFilters,
			GeneralStatus generalStatus, Book book) {
		this(showStatusFilters, false, generalStatus, null, book);
	}

	/**
	 * Constructor.
	 */
	public ReservationsPanel(boolean showStatusFilters,
			boolean showAssociationFilters, GeneralStatus generalStatus,
			User user, Book book) {
		this.user = user;
		this.book = book;
		this.reservation = null;
		this.generalStatus = generalStatus;
		model = new ReservationsTableModel(generalStatus, user, book);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"), "Reservations: "
						+ model.getRowCount() + " records found",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);

		// Creating StatusFilterPanel and registering itself in it
		statusFilterPanel = new StatusFilterPanel(generalStatus);
		statusFilterPanel.register(this);

		// Creating BookBasicSearchPanel and registering itself in it
		associationFilterPanel = new AssociationFilterPanel(true, true);
		associationFilterPanel.register(this);

		showUI(showStatusFilters, showAssociationFilters);
	}

	/**
	 * Setting UI.
	 * 
	 * @param showStatusFilters
	 * @param showAssociationFilters
	 */
	private void showUI(boolean showStatusFilters,
			boolean showAssociationFilters) {
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);

		if (showStatusFilters || showAssociationFilters) {
			add(getFilterPane(showStatusFilters, showAssociationFilters),
					BorderLayout.PAGE_START);
		}
		add(getTablePane(), BorderLayout.CENTER);
		add(getActionButtonPane(), BorderLayout.PAGE_END);
	}

	/**
	 * JScrollPane with table of reservations.
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getTablePane() {
		table = new JTable(model);

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (table.getSelectedRow() > -1) {
							// Set fine from selected row
							reservation = (Reservation) model
									.getReservation(table.getSelectedRow());

							viewReservationButton.setEnabled(true);
							viewBookButton.setEnabled(true);
							viewUserButton.setEnabled(true);

							// Setting button visibility
							if (reservation.getGeneralStatus() == GeneralStatus.ACTIVE) {
								cancelReservationButton.setEnabled(true);
							} else {
								cancelReservationButton.setEnabled(false);
							}
						} else {
							reservation = null;

							viewReservationButton.setEnabled(false);
							viewBookButton.setEnabled(false);
							viewUserButton.setEnabled(false);

							cancelReservationButton.setEnabled(false);
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

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);

		return scrollPane;
	}

	/**
	 * Panel with buttons to perform tasks on selected reservation.
	 * 
	 * @return JPanel
	 */
	private JPanel getActionButtonPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		viewReservationButton = new JButton("Reservation Details");
		viewReservationButton.setEnabled(false);
		viewReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							new ReservationDetailsUI(reservation);
						} catch (Exception e) {
							UIDisplayManager.displayErrorMessage(
									ReservationsPanel.this,
									"Can't view reservation!");
						}
					}
				});
			}
		});

		viewBookButton = new JButton("View Book Details");
		viewBookButton.setEnabled(false);
		viewBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						Book temp = AdminBooks.getInstance().getBook(
								reservation.getBookID());
						if (temp != null) {
							new BookDetailsUI(temp);
						}
					}
				});
			}
		});

		viewUserButton = new JButton("View User Details");
		viewUserButton.setEnabled(false);
		viewUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						User temp = AdminUsers.getInstance().getUser(
								reservation.getUserID());
						if (temp != null) {
							new UserDetailsUI(temp);
						}
					}
				});
			}
		});

		cancelReservationButton = new JButton("Cancel Reservation");
		cancelReservationButton.setEnabled(false);
		cancelReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if (AdminReservations.getInstance().remove(reservation)) {
							// Displaying confirmation dialog
							JOptionPane.showMessageDialog(null,
									"Reservation cancelled!",
									"Reservation Cancelation Message",
									JOptionPane.INFORMATION_MESSAGE);

							model.replaceData(generalStatus, user, book);
						}
					}
				});
			}
		});

		// Adding buttons to panel
		panel.add(viewReservationButton);
		panel.add(viewBookButton);
		panel.add(viewUserButton);
		panel.add(cancelReservationButton);

		return panel;
	}

	/**
	 * This methods displays filters. Filters can be enabled or disabled via
	 * boolean parameters.
	 * 
	 * @param showStatusFilters
	 * @param showAssociationFilters
	 * @return JPanel
	 */
	private JPanel getFilterPane(boolean showStatusFilters,
			boolean showAssociationFilters) {
		JPanel panel = new JPanel(new SpringLayout());

		if (showStatusFilters) {
			panel.add(statusFilterPanel);
		}
		if (showAssociationFilters) {
			panel.add(associationFilterPanel);
		}

		SpringUtilities.makeCompactGrid(panel, 1, panel.getComponentCount(), 5,
				5, 5, 5);

		return panel;
	}

	/**
	 * This method is asking table model to update it's data passing current
	 * generalStatus, user and book data as parameters.
	 */
	public void refreshTable() {
		// Updating table data
		model.replaceData(generalStatus, user, book);

		// Updating titled border
		updateTitledBorder();
	}

	/**
	 * This method requests to update data in table model.
	 */
	@Override
	public void update(String className, Object arg) {
		if (className.equals("StatusFilterPanel")) {
			generalStatus = (GeneralStatus) arg;
		}
		if (className.equals("UserBasicSearchPanel")) {
			user = (User) arg;
		}
		if (className.equals("BookBasicSearchPanel")) {
			book = (Book) arg;
		}
		if (className.equals("AssociationFilterPanel")) {
			if (((String) arg).equals("none")) {
				user = null;
				book = null;
			} else if (((String) arg).equals("user")) {
				book = null;
			} else if (((String) arg).equals("book")) {
				user = null;
			}
		}

		// Updating table model
		model.replaceData(generalStatus, user, book);

		// Updating titled border
		updateTitledBorder();
	}

	/**
	 * This method updates title in titled border.
	 */
	private void updateTitledBorder() {
		// Setting title for titled border
		titledBorder.setTitle("Reservations: " + model.getRowCount()
				+ " records found");

		// Updating UI
		repaint();
	}
}