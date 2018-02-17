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
import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.ItemStatus;

/**
 * Input dialog for new <code>Reservation</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class NewReservationUI extends JDialog {

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
	
	public NewReservationUI() {
		this(null, null);
	}
	
	public NewReservationUI(User user) {
		this(null, user);
	}
	
	public NewReservationUI(Book book) {
		this(book, null);
	}
	
	public NewReservationUI(Book book, User user) {
		this.book = book;
		this.user = user;
		this.okPressed = false;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("New Reservation");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);
		
		add(this.getMainPane(), BorderLayout.CENTER);
		add(this.getButtonPane(), BorderLayout.PAGE_END);
		
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
		
		pane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.setLayout(new BorderLayout(10, 10));
		pane.add(left, BorderLayout.LINE_START);
		pane.add(center, BorderLayout.CENTER);
		pane.add(right, BorderLayout.EAST);
		
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
				if(reserveBook()){
					okPressed = true;
					
					UIDisplayManager.displayInformationMessage(
							NewReservationUI.this,
							"Book "+book.getTitle()+" reserved successfully for "+user.getName());
					
					dispose();
				} else {
					okPressed = false;
					
					UIDisplayManager.displayErrorMessage(
							NewReservationUI.this,
							"Error occur while reserving book "+book.getTitle()+" for "+user.getName());
					
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
					
					UserFinderUI dialog;;
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
										NewReservationUI.this,
										"Found User is NULL! \n"
										+ "Please try to find user again!!!");
							}
						} else {
							txtUser.setEditable(true);
							okButton.setEnabled(false);
						}
					} catch (Exception e) {
						UIDisplayManager.displayErrorMessage(
								NewReservationUI.this,
								"Error occur while setting user details!");
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							NewReservationUI.this,
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
								// Checking that book generalStatus is not RESERVED
								if(temp.getStatus() != ItemStatus.RESERVED){
									// Checking that book is not in reservation list
									if(!AdminReservations.getInstance().isReserved(temp)){
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
												NewReservationUI.this,
												"Found Book is RESERVED! \n"
												+ "Please try to find book again!!!");
									}
								} else {
									UIDisplayManager.displayErrorMessage(
											NewReservationUI.this,
											"Found Book generalStatus is RESERVED! \n"
											+ "Please try to find book again!!!");
								}
							} else {
								UIDisplayManager.displayErrorMessage(
										NewReservationUI.this,
										"Found Book is NULL! \n"
										+ "Please try to find book again!!!");
							}
						} else {
							txtBook.setEditable(true);
							okButton.setEnabled(false);
						}
					} catch (Exception e) {
						UIDisplayManager.displayErrorMessage(
								NewReservationUI.this,
								"Error occur while setting book details!");
					}
				} catch (Exception e) {
					UIDisplayManager.displayErrorMessage(
							NewReservationUI.this,
							"Can't display BookFinderUI!");
				}
			}
		});
	}
	
	/**
	 * Requests to reserve book in database.
	 * 
	 * @return boolean
	 */
	private boolean reserveBook() {
		return AdminBooks.getInstance().reserveBook(book, user);
	}
	
	public Book getBook() {
		return book;
	}
	
	public User getUser() {
		return user;
	}
	
	private boolean isBookSet() {
		if(book != null){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isUserSet() {
		if(user != null){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}