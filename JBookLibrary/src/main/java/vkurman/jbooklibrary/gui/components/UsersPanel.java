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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.Borrower;
import vkurman.jbooklibrary.core.Librarian;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.EmploymentTerminationReason;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.gui.EmploymentTerminationUI;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.gui.UserDetailsUI;
import vkurman.jbooklibrary.gui.filters.StatusFilterPanel;
import vkurman.jbooklibrary.gui.filters.UserFilterPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;

/**
 * Panel that displays list of users and buttons to perform actions on selected
 * user.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UsersPanel extends JPanel implements FilterObserver {

	private static final long serialVersionUID = 5330682321221615839L;
	private GeneralStatus generalStatus;
	private String userType;
	private User user;
	private StatusFilterPanel statusFilterPanel;
	private UserFilterPanel userFilterPanel;
	private JTable table;
	private JButton viewUserButton, deactivateUserButton, reactivateUserButton;
	private UsersTableModel model;
	private TitledBorder titledBorder;

	public UsersPanel() {
		this("Borrower", GeneralStatus.ACTIVE);
	}

	public UsersPanel(String userType) {
		this(userType, GeneralStatus.ACTIVE);
	}

	public UsersPanel(GeneralStatus generalStatus) {
		this("Borrower", generalStatus);
	}

	private UsersPanel(String userType, GeneralStatus generalStatus) {
		this.generalStatus = generalStatus;
		this.userType = userType;
		model = new UsersTableModel(userType, generalStatus);
		titledBorder = BorderFactory.createTitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Users: " + model.getRowCount() + " records found",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);

		// Creating StatusFilterPanel and registering itself in it
		statusFilterPanel = new StatusFilterPanel(generalStatus);
		statusFilterPanel.register(this);

		// Creating StatusFilterPanel and registering itself in it
		userFilterPanel = new UserFilterPanel(userType);
		userFilterPanel.register(this);

		showUI();
	}

	private void showUI() {
		setLayout(new BorderLayout(5, 5));
		setBorder(titledBorder);

		add(getFilterPane(), BorderLayout.PAGE_START);
		add(getTablePane(), BorderLayout.CENTER);
		add(getActionButtonPane(), BorderLayout.PAGE_END);
	}

	private JScrollPane getTablePane() {
		table = new JTable(model);

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (table.getSelectedRow() > -1) {
							// Set fine from selected row
							user = (User) model.getUser(table.getSelectedRow());
							viewUserButton.setEnabled(true);

							if (user.isActive()) {
								deactivateUserButton.setEnabled(true);
								reactivateUserButton.setEnabled(false);
							} else {
								deactivateUserButton.setEnabled(false);
								reactivateUserButton.setEnabled(true);
							}
						} else {
							user = null;
							viewUserButton.setEnabled(false);
							deactivateUserButton.setEnabled(false);
							reactivateUserButton.setEnabled(false);
						}
					}
				});

		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);
		table.getColumnModel().getColumn(5).setPreferredWidth(75);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(AdminPrefs.DIMENSION_TABLE_PANEL);
		table.setFillsViewportHeight(true);

		return scrollPane;
	}

	private JPanel getActionButtonPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		viewUserButton = new JButton("View");
		viewUserButton.setActionCommand("View");
		viewUserButton.setEnabled(false);
		viewUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				viewUserDetails();
			}
		});

		deactivateUserButton = new JButton("Deactivate");
		deactivateUserButton.setActionCommand("Deactivate");
		deactivateUserButton.setEnabled(false);
		deactivateUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				deactivateUser();
			}
		});

		reactivateUserButton = new JButton("Reactivate");
		reactivateUserButton.setActionCommand("Reactivate");
		reactivateUserButton.setEnabled(false);
		reactivateUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				reactivateUser();
			}
		});

		panel.add(viewUserButton);
		panel.add(deactivateUserButton);
		panel.add(reactivateUserButton);

		return panel;
	}

	/**
	 * Displays window with user details.
	 */
	private void viewUserDetails() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDetailsUI dialog = new UserDetailsUI(user);
					if (!dialog.isShowing()) {
						model.fireTableRowsUpdated(table.getSelectedRow(),
								table.getSelectedRow());
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(UsersPanel.this,
							"Can't display UserDetailsUI!");
				}
			}
		});
	}

	private void deactivateUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int n = JOptionPane.showConfirmDialog(null,
							"Do you want to deactivate user: " + user.getName()
									+ "?", "User Deactivation Dialog",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						if (user.getClass().getSimpleName().equals("Borrower")) {
							if (AdminUsers.getInstance().removeBorrower(
									(Borrower) user)) {
								if (generalStatus == GeneralStatus.ACTIVE) {
									model.removeUser(user);
									updateTitledBorder();
								} else {
									model.updateUser(user);
								}
							}
						} else if (user.getClass().getSimpleName()
								.equals("Librarian")) {
							try {
								EmploymentTerminationUI dialog = new EmploymentTerminationUI(
										(Librarian) user);
								if (dialog.isOkPressed()) {
									EmploymentTerminationReason reason = dialog
											.getReason();
									if (AdminUsers.getInstance()
											.removeLibrarian((Librarian) user,
													reason)) {
										if (generalStatus == GeneralStatus.ACTIVE) {
											model.removeUser(user);
											updateTitledBorder();
										} else {
											model.updateUser(user);
										}
									}
								}
							} catch (Exception e) {
								UIDisplayManager
										.displayErrorMessage(UsersPanel.this,
												"Can't display EmploymentTerminationUI!");
							}
						} else {
							UIDisplayManager
									.displayErrorMessage(UsersPanel.this,
											"Can't confirm user type!");
						}
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(UsersPanel.this,
							"Can't display DeactivateUserUI!");
				}
			}
		});

		if (generalStatus != GeneralStatus.INACTIVE) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					deactivateUserButton.setEnabled(false);
					reactivateUserButton.setEnabled(true);
				}
			});
		}
	}

	private void reactivateUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int n = JOptionPane.showConfirmDialog(
							null,
							"Do you want to re-activate user: "
									+ user.getName() + "?",
							"User Reactivation Dialog",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						// Reactivating user in database
						boolean success;
						if (user.getClass().getSimpleName().equals("Borrower")) {
							success = AdminUsers.getInstance()
									.reactivateBorrower((Borrower) user);
						} else if (user.getClass().getSimpleName()
								.equals("Librarian")) {
							success = AdminUsers.getInstance()
									.reactivateLibrarian((Librarian) user);
						} else {
							success = false;

							UIDisplayManager
									.displayErrorMessage(UsersPanel.this,
											"Can't confirm user type!");
						}
						// Updating table model
						if (success) {
							UIDisplayManager.displayInformationMessage(
									UsersPanel.this,
									"User has been made active!");

							if (generalStatus == GeneralStatus.ACTIVE) {
								model.removeUser(user);
								updateTitledBorder();
							} else {
								model.updateUser(user);
							}

							viewUserDetails();
						}
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(UsersPanel.this,
							"Can't display Re-Activate User Dialog!");
				}
			}
		});

		if (generalStatus != GeneralStatus.INACTIVE) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					deactivateUserButton.setEnabled(true);
					reactivateUserButton.setEnabled(false);
				}
			});
		}
	}

	/**
	 * Creates and returns filter panel containing generalStatus filter and user
	 * filter.
	 * 
	 * @return JPanel
	 */
	private JPanel getFilterPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		panel.add(statusFilterPanel);
		panel.add(userFilterPanel);

		return panel;
	}

	/**
	 * Adds user to table and updates title on the border.
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		model.addUser(user);

		updateTitledBorder();
	}

	@Override
	public void update(String className, Object arg) {
		if (className.equals("UserFilterPanel")) {
			userFilterPanel.resetSearchPanel();

			this.userType = (String) arg;
			model.replaceData(userType, generalStatus);
		}
		if (className.equals("StatusFilterPanel")) {
			userFilterPanel.resetSearchPanel();

			this.generalStatus = (GeneralStatus) arg;
			model.replaceData(userType, generalStatus);
		}
		if (className.equals("UserBasicSearchPanel")) {
			if (arg != null) {
				model.replaceData((User) arg);
			} else {
				model.replaceData(userType, generalStatus);
			}
		}

		// Updating titled border
		updateTitledBorder();
	}

	/**
	 * This method is updating title in the titled border.
	 */
	private void updateTitledBorder() {
		// Updating title for titled border
		titledBorder.setTitle("Users: " + model.getRowCount()
				+ " records found");

		// Updating UI
		repaint();
	}
}