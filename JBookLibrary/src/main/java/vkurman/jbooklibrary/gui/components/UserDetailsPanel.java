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
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.AdminUsers;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.enums.Sex;
import vkurman.jbooklibrary.enums.Withdrawal;
import vkurman.jbooklibrary.gui.EnumSexInputDialog;
import vkurman.jbooklibrary.gui.EnumStatusInputDialog;
import vkurman.jbooklibrary.gui.EnumWithdrawalInputDialog;
import vkurman.jbooklibrary.gui.TextInputDialog;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.jbasiccalendar.JBasicCalendarUI;

/**
 * <code>JPanel</code> that has all basic information about
 * specified <code>User</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserDetailsPanel extends JPanel {
	
	private static final long serialVersionUID = 8125603231425348476L;
	private User user;
	private JTextField txtTitle,
		txtDegree,
		txtInstitution,
		txtFirstname,
		txtMiddlename,
		txtSurname,
		txtSex,
		txtStatus,
		txtDOB,
		txtPrivateEmail,
		txtPrivatePhone,
		txtPrivateMobile,
		txtPrivateFax,
		txtOfficeEmail,
		txtOfficePhone,
		txtOfficeMobile,
		txtOfficeFax,
		txtUrl,
		txtUserCategory,
		txtWithdrawal;
	
	/**
	 * Constructor.
	 * 
	 * @param user
	 */
	public UserDetailsPanel(User user) {
		this.user = user;
		showUI();
	}
	
	/**
	 * Creates and displays panel.
	 */
	private void showUI(){
		setPreferredSize(new Dimension(1100, 560));
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(5, 5));
		add(getDataPanel(), BorderLayout.CENTER);
	}
	
	private JPanel getDataPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		// Row 1
		JPanel r1 = new JPanel();
		r1.setLayout(new BorderLayout(5, 5));
		
		JPanel r1l = new JPanel();
		r1l.setLayout(new GridLayout(6, 1, 5, 5));
		r1l.add(getUserIDPanel());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2, 5, 5));
		p1.add(getTitlePanel());
		p1.add(getSexPanel());
		r1l.add(p1);
		
		r1l.add(getFirstnamePanel());
		r1l.add(getMiddlenamePanel());
		r1l.add(getSurnamePanel());
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2, 5, 5));
		p2.add(getDobPanel());
		p2.add(getStatusPanel());
		r1l.add(p2);
		
		r1.add(r1l, BorderLayout.CENTER);
		r1.add(new AddressPanel(user), BorderLayout.LINE_END);
		panel.add(r1);
		
		
		// Row 2
		JPanel r2 = new JPanel();
		r2.setLayout(new BorderLayout(5, 5));
		
		JPanel r2r = new JPanel();
		r2r.setLayout(new GridLayout(10, 1, 5, 5));
		r2r.add(getDegreePanel());
		r2r.add(getInstitutionPanel());
		
		r2r.add(getPrivateEmail());
		r2r.add(getOfficeEmail());
		
		JPanel r2r1 = new JPanel();
		r2r1.setLayout(new GridLayout(1, 2, 5, 5));
		r2r1.add(getPrivatePhone());
		r2r1.add(getOfficePhone());
		r2r.add(r2r1);
		
		JPanel r2r2 = new JPanel();
		r2r2.setLayout(new GridLayout(1, 2, 5, 5));
		r2r2.add(getPrivateMobile());
		r2r2.add(getOfficeMobile());
		r2r.add(r2r2);
		
		r2r.add(getPrivateFax());
		r2r.add(getOfficeFax());
		
		r2r.add(getUrl());
		
		JPanel r2r3 = new JPanel();
		r2r3.setLayout(new GridLayout(1, 2, 5, 5));
		r2r3.add(getUserCategory());
		r2r3.add(getWithdrawalPanel());
		r2r.add(r2r3);
		
		r2.add(new IDCardDetailsPanel(user), BorderLayout.LINE_START);
		r2.add(r2r, BorderLayout.CENTER);
		panel.add(r2);
		
		return panel;
	}
	
	private JPanel getUserIDPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		JTextField txtUserID = new JTextField(Long.toString(user.getUserID()));
		txtUserID.setEditable(false);
		txtUserID.setColumns(12);
		
		panel.add(new JLabel("User ID:"), BorderLayout.LINE_START);
		panel.add(txtUserID, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel getTitlePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtTitle = new JTextField(user.getTitle());
		txtTitle.setEditable(false);
		txtTitle.setColumns(12);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getTitle());
					if(dialog.isOkPressed()){
						user.setTitle(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "TITLE", user.getTitle());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "TITLE", user.getTitle());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtTitle.setText(user.getTitle());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Title:"), BorderLayout.LINE_START);
		panel.add(txtTitle, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getInstitutionPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtInstitution = new JTextField(user.getInstitution());
		txtInstitution.setEditable(false);
		txtInstitution.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getInstitution());
					if(dialog.isOkPressed()){
						user.setInstitution(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "INSTITUTION", user.getInstitution());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "INSTITUTION", user.getInstitution());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtInstitution.setText(user.getInstitution());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Institution:"), BorderLayout.LINE_START);
		panel.add(txtInstitution, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getFirstnamePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtFirstname = new JTextField(user.getFirstname());
		txtFirstname.setEditable(false);
		txtFirstname.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getFirstname());
					if(dialog.isOkPressed()){
						user.setFirstname(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "FIRSTNAME", user.getFirstname());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "FIRSTNAME", user.getFirstname());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtFirstname.setText(user.getFirstname());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Firstname:"), BorderLayout.LINE_START);
		panel.add(txtFirstname, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getDegreePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtDegree = new JTextField(user.getDegree());
		txtDegree.setEditable(false);
		txtDegree.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getDegree());
					if(dialog.isOkPressed()){
						user.setDegree(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "DEGREE", user.getDegree());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "DEGREE", user.getDegree());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtDegree.setText(user.getDegree());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Degree:"), BorderLayout.LINE_START);
		panel.add(txtDegree, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getMiddlenamePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtMiddlename = new JTextField(user.getMiddlename());
		txtMiddlename.setEditable(false);
		txtMiddlename.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getMiddlename());
					if(dialog.isOkPressed()){
						user.setMiddlename(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "MIDDLENAME", user.getMiddlename());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "MIDDLENAME", user.getMiddlename());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtMiddlename.setText(user.getMiddlename());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Middlename:"), BorderLayout.LINE_START);
		panel.add(txtMiddlename, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getSurnamePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtSurname = new JTextField(user.getSurname());
		txtSurname.setEditable(false);
		txtSurname.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getSurname());
					if(dialog.isOkPressed()){
						user.setSurname(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "SURNAME", user.getSurname());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "SURNAME", user.getSurname());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSurname.setText(user.getSurname());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Surname:"), BorderLayout.LINE_START);
		panel.add(txtSurname, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getSexPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		// Setting user sex in case if it not set before
		if(user.getSex() == null){
			user.setSex(Sex.UNSPECIFIED);
		}
		
		txtSex = new JTextField(user.getSex().toString());
		txtSex.setEditable(false);
		txtSex.setColumns(12);
		
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					EnumSexInputDialog dialog = new EnumSexInputDialog(user.getSex());
					if(dialog.isOkPressed()){
						user.setSex(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "SEX", user.getSex().toString());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "SEX", user.getSex().toString());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSex.setText(user.getSex().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Sex:"), BorderLayout.LINE_START);
		panel.add(txtSex, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getStatusPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtStatus = new JTextField(user.getGeneralStatus().toString());
		txtStatus.setEditable(false);
		txtStatus.setColumns(12);
		
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					EnumStatusInputDialog dialog = new EnumStatusInputDialog(user.getGeneralStatus());
					if(dialog.isOkPressed()){
						user.setGeneralStatus(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "STATUS", user.getGeneralStatus().toString());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "STATUS", user.getGeneralStatus().toString());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtStatus.setText(user.getGeneralStatus().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Status:"), BorderLayout.LINE_START);
		panel.add(txtStatus, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getDobPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtDOB = new JTextField((user.getDob() == null) ?
					"" : BasicLibraryDateFormatter.formatDate(user.getDob()));
		txtDOB.setEditable(false);
		txtDOB.setColumns(12);
		
		JButton edit = new JButton("Edit");
		edit.setEnabled(true);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				JBasicCalendarUI dialog;
				
				if(user.getDob() != null){
					dialog = new JBasicCalendarUI(UserDetailsPanel.this, user.getDob());
				} else {
					dialog = new JBasicCalendarUI();
				}
				
				dialog.setVisible(true);
				
				if(dialog.isOkPressed()){
					user.setDob(dialog.getCalendar());
					
					// Updating database
					new SwingWorker<Void, Void>(){
						@Override
						protected Void doInBackground() throws Exception {
							// Checking the user class
							if(user.getClass().getSimpleName().equals("Borrower")){
								AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "DOB", user.getDob().getTime());
							} else {
								AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "DOB", user.getDob().getTime());
							}
							return null;
						}
					}.execute();
					
					// Updating UI text field
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								txtDOB.setText(BasicLibraryDateFormatter.formatDate(user.getDob()));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		
		panel.add(new JLabel("D.O.B.:"), BorderLayout.LINE_START);
		panel.add(txtDOB, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getPrivateEmail(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtPrivateEmail = new JTextField(user.getPrivateEmail());
		txtPrivateEmail.setEditable(false);
		txtPrivateEmail.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getPrivateEmail());
					if(dialog.isOkPressed()){
						user.setPrivateEmail(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "PRIVATEEMAIL", user.getPrivateEmail());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "PRIVATEEMAIL", user.getPrivateEmail());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPrivateEmail.setText(user.getPrivateEmail());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Private Email:"), BorderLayout.LINE_START);
		panel.add(txtPrivateEmail, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getPrivatePhone(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtPrivatePhone = new JTextField(user.getPrivatePhone());
		txtPrivatePhone.setEditable(false);
		txtPrivatePhone.setColumns(12);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getPrivatePhone());
					if(dialog.isOkPressed()){
						user.setPrivatePhone(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "PRIVATEPHONE", user.getPrivatePhone());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "PRIVATEPHONE", user.getPrivatePhone());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPrivatePhone.setText(user.getPrivatePhone());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Private Phone:"), BorderLayout.LINE_START);
		panel.add(txtPrivatePhone, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getPrivateMobile(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtPrivateMobile = new JTextField(user.getPrivateMobile());
		txtPrivateMobile.setEditable(false);
		txtPrivateMobile.setColumns(12);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getPrivateMobile());
					if(dialog.isOkPressed()){
						user.setPrivateMobile(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "PRIVATEMOBILE", user.getPrivateMobile());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "PRIVATEMOBILE", user.getPrivateMobile());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPrivateMobile.setText(user.getPrivateMobile());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Private Mobile:"), BorderLayout.LINE_START);
		panel.add(txtPrivateMobile, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getPrivateFax(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtPrivateFax = new JTextField(user.getPrivateFax());
		txtPrivateFax.setEditable(false);
		txtPrivateFax.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getPrivateFax());
					if(dialog.isOkPressed()){
						user.setPrivateFax(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "PRIVATEFAX", user.getPrivateFax());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "PRIVATEFAX", user.getPrivateFax());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPrivateFax.setText(user.getPrivateFax());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Private Fax:"), BorderLayout.LINE_START);
		panel.add(txtPrivateFax, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getOfficeEmail(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtOfficeEmail = new JTextField(user.getOfficeEmail());
		txtOfficeEmail.setEditable(false);
		txtOfficeEmail.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getOfficeEmail());
					if(dialog.isOkPressed()){
						user.setOfficeEmail(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "OFFICEEMAIL", user.getOfficeEmail());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "OFFICEEMAIL", user.getOfficeEmail());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtOfficeEmail.setText(user.getOfficeEmail());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Office Email:"), BorderLayout.LINE_START);
		panel.add(txtOfficeEmail, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getOfficePhone(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtOfficePhone = new JTextField(user.getOfficePhone());
		txtOfficePhone.setEditable(false);
		txtOfficePhone.setColumns(12);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getOfficePhone());
					if(dialog.isOkPressed()){
						user.setOfficePhone(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "OFFICEPHONE", user.getOfficePhone());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "OFFICEPHONE", user.getOfficePhone());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtOfficePhone.setText(user.getOfficePhone());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Office Phone:"), BorderLayout.LINE_START);
		panel.add(txtOfficePhone, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getOfficeMobile(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtOfficeMobile = new JTextField(user.getOfficeMobile());
		txtOfficeMobile.setEditable(false);
		txtOfficeMobile.setColumns(12);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getOfficeMobile());
					if(dialog.isOkPressed()){
						user.setOfficeMobile(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "OFFICEMOBILE", user.getOfficeMobile());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "OFFICEMOBILE", user.getOfficeMobile());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtOfficeMobile.setText(user.getOfficeMobile());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Office Mobile:"), BorderLayout.LINE_START);
		panel.add(txtOfficeMobile, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getOfficeFax(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtOfficeFax = new JTextField(user.getOfficeFax());
		txtOfficeFax.setEditable(false);
		txtOfficeFax.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getOfficeFax());
					if(dialog.isOkPressed()){
						user.setOfficeFax(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "OFFICEFAX", user.getOfficeFax());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "OFFICEFAX", user.getOfficeFax());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtOfficeFax.setText(user.getOfficeFax());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Office Fax:"), BorderLayout.LINE_START);
		panel.add(txtOfficeFax, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getUrl(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtUrl = new JTextField(user.getUrl());
		txtUrl.setEditable(false);
		txtUrl.setColumns(20);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getUrl());
					if(dialog.isOkPressed()){
						user.setUrl(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "URL", user.getUrl());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "URL", user.getUrl());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtUrl.setText(user.getUrl());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("URL:"), BorderLayout.LINE_START);
		panel.add(txtUrl, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getUserCategory(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		txtUserCategory = new JTextField(user.getUserCategory());
		txtUserCategory.setEditable(false);
		txtUserCategory.setColumns(14);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(null, user.getUserCategory());
					if(dialog.isOkPressed()){
						user.setUserCategory(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "USERCATEGORY", user.getUserCategory());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "USERCATEGORY", user.getUserCategory());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtUserCategory.setText(user.getUserCategory());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("User Category:"), BorderLayout.LINE_START);
		panel.add(txtUserCategory, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel getWithdrawalPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		
		// Setting user Withdrawal in case if it not set before
		if(user.getWithdrawal() == null){
			user.setWithdrawal(Withdrawal.NONE);
		}
		txtWithdrawal = new JTextField(user.getWithdrawal().toString());
		txtWithdrawal.setEditable(false);
		txtWithdrawal.setColumns(14);
		
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					EnumWithdrawalInputDialog dialog = new EnumWithdrawalInputDialog(user.getWithdrawal());
					if(dialog.isOkPressed()){
						user.setWithdrawal(dialog.getInput());
						
						// Updating database
						new SwingWorker<Void, Void>(){
							@Override
							protected Void doInBackground() throws Exception {
								if(isLibrarian(user)){
									AdminUsers.getInstance().updateLibrarianDB(user.getUserID(), "WITHDRAWAL", user.getWithdrawal().toString());
								} else {
									AdminUsers.getInstance().updateBorrowerDB(user.getUserID(), "WITHDRAWAL", user.getWithdrawal().toString());
								}
								return null;
							}
						}.execute();
						
						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtWithdrawal.setText(user.getWithdrawal().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		panel.add(new JLabel("Withdrawal:"), BorderLayout.LINE_START);
		panel.add(txtWithdrawal, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);
		
		return panel;
	}
	
	/**
	 * Returns <code>true</code> is specified user is of class
	 * <code>Librarian</code>
	 * 
	 * @param user
	 * @return boolean
	 */
	private boolean isLibrarian(User user){
		if(user.getClass().getSimpleName().equals("Librarian")){
			return true;
		} else {
			return false;
		}
	}
}