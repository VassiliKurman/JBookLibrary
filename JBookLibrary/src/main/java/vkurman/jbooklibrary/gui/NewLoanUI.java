package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminLoans;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.ItemStatus;

/**
 * Input dialog for new <code>Loan</code>
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class NewLoanUI extends JDialog {

	private static final long serialVersionUID = 8991276525347186533L;
	private Book book;
	private User user;
	private JPanel contentPane;
	
	private JButton cancelButton;
	private JButton okButton;
	private JButton findBookButton;
	private JButton findUserButton;
	private JTextField txtBook;
	private JTextField txtUser;
	
	private boolean okPressed;
	
	public NewLoanUI() {
		this(null, null);
	}
	
	public NewLoanUI(User user) {
		this(null, user);
	}
	
	public NewLoanUI(Book book) {
		this(book, null);
	}
	
	public NewLoanUI(Book book, User user) {
		this.book = book;
		this.user = user;
		this.okPressed = false;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("New Loan");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);
		
		contentPane.add(this.getMainPane(), BorderLayout.CENTER);
		contentPane.add(this.getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getMainPane() {
		JPanel pane = new JPanel();
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(2, 1, 5, 5));
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2, 1, 5, 5));
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(2, 1, 5, 5));
		
		pane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.setLayout(new BorderLayout(10, 10));
		pane.add(left, BorderLayout.LINE_START);
		pane.add(center, BorderLayout.CENTER);
		pane.add(right, BorderLayout.EAST);
		
		{
			JLabel lblUser = new JLabel("User:");
			left.add(lblUser);
			
			txtUser = new JTextField(36);
			center.add(txtUser);
			
			findUserButton = new JButton("Find User");
			findUserButton.setActionCommand("findUser");
			findUserButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					findUser();
				}
			});
			
			if(isUserSet()){
				txtUser.setEditable(false);
				txtUser.setText(user.getName());
				findUserButton.setEnabled(false);
			} else {
				findUserButton.setEnabled(true);
			}
			
			right.add(findUserButton);
		}
		{
			JLabel lblBook = new JLabel("Book:");
			left.add(lblBook);
			
			txtBook = new JTextField(36);
			center.add(txtBook);
			
			findBookButton = new JButton("Find Book");
			findBookButton.setActionCommand("findBook");
			findBookButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					findBook();
				}
			});
			
			if(isBookSet()){
				txtBook.setEditable(false);
				txtBook.setText(book.toString());
				findBookButton.setEnabled(false);
			} else {
				findBookButton.setEnabled(true);
			}
			
			right.add(findBookButton);
		}
		
		return pane;
	}
	
	private JPanel getButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if(borrowBook()){
					okPressed = true;
					
					UIDisplayManager.displayInformationMessage(
							NewLoanUI.this,
							"Book "+book.getTitle()+" borrowed successfully by "+user.getName());
					
					dispose();
				} else {
					okPressed = false;
					
					UIDisplayManager.displayErrorMessage(
							NewLoanUI.this,
							"Error occur while borrowing book "+book.getTitle()+" by "+user.getName());
					
					dispose();
				}
			}
		});
		if(isBookSet() && isUserSet()){
			okButton.setEnabled(true);
			getRootPane().setDefaultButton(okButton);
		} else {
			okButton.setEnabled(false);
			getRootPane().setDefaultButton(cancelButton);
		}
		buttonPane.add(okButton);
		
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
		
		return buttonPane;
	}
	
	private void findUser() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject u = new TransObject();
					
					UserFinderUI dialog;
					if (txtUser.getText().isEmpty()) {
						dialog = new UserFinderUI(u);
					} else {
						dialog = new UserFinderUI(u, txtUser.getText());
					}
					
					try {
						if(dialog.isOKPressed()){
							User temp = (User) u.getObject();
							if (temp != null) {
								user = temp;
								txtUser.setEditable(false);
								txtUser.setText(temp.getName());
								findUserButton.setEnabled(false);
								
								if(isBookSet()){
									okButton.setEnabled(true);
								} else {
									okButton.setEnabled(false);
								}
							} else {
								UIDisplayManager.displayErrorMessage(
										NewLoanUI.this,
										"Found User is NULL! \n"
										+ "Please try to find user again!!!");
							}
						} else {
							txtUser.setEditable(true);
							okButton.setEnabled(false);
						}
					} catch (Exception e) {
						UIDisplayManager.displayErrorMessage(
								NewLoanUI.this,
								"Error occur while setting user details!");
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							NewLoanUI.this,
							"Can't display UserFinderUI!");
				}
			}
		});
	}
	
	private void findBook() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject b = new TransObject();
					BookFinderUI dialog = new BookFinderUI(b);
					
					try {
						if(dialog.isOKPressed()){
							Book temp = (Book) b.getObject();
							if (temp != null) {
								// Checking that book generalStatus is not ONLOAN
								if(temp.getStatus() != ItemStatus.ONLOAN){
									// Checking that book is not in active loan list
									if(!AdminLoans.getInstance().isOnLoan(temp)){
										book = temp;
										txtBook.setEditable(false);
										txtBook.setText(temp.toString());
										findBookButton.setEnabled(false);
										
										if(isUserSet()){
											okButton.setEnabled(true);
										} else {
											okButton.setEnabled(false);
										}
									} else {
										UIDisplayManager.displayErrorMessage(
												NewLoanUI.this,
												"Found Book is ONLOAN! \n"
												+ "Please try to find book again!!!");
									}
								} else {
									UIDisplayManager.displayErrorMessage(
											NewLoanUI.this,
											"Found Book generalStatus is ONLOAN! \n"
											+ "Please try to find book again!!!");
								}
							} else {
								UIDisplayManager.displayErrorMessage(
										NewLoanUI.this,
										"Found Book is NULL! \n"
										+ "Please try to find book again!!!");
							}
						} else {
							txtBook.setEditable(true);
							okButton.setEnabled(false);
						}
					} catch (Exception e) {
						UIDisplayManager.displayErrorMessage(
								NewLoanUI.this,
								"Error occur while setting book details!");
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							NewLoanUI.this,
							"Can't display BookFinderUI!");
				}
			}
		});
	}
	
	/**
	 * Requesting books administrator to borrow the book.
	 * 
	 * @return boolean
	 */
	private boolean borrowBook() {
		return AdminBooks.getInstance().borrowBook(user, book);
	}
	
	/**
	 * Returns book.
	 * 
	 * @return Book
	 */
	public Book getBook() {
		return book;
	}
	
	/**
	 * Returns signed in user.
	 * 
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	private boolean isBookSet() {
		return (book != null) ? true : false;
	}
	
	private boolean isUserSet() {
		return (user != null) ? true :false;
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}