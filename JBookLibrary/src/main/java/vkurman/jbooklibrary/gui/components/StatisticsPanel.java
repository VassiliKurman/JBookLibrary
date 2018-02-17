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
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.AdminStats;

/**
 * Most basic statistics panel.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = -6193370941513836591L;
	
	public StatisticsPanel() {
		showUI();
	}
	
	private void showUI() {
		setLayout(new GridLayout(2, 1, 10, 10));
		
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout(5, 5));
		p2.add(getBooksStatistics(), BorderLayout.CENTER);
		p2.add(getPaymentsStatistics(), BorderLayout.PAGE_END);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout(5, 5));
		p3.add(getLoansStatistics(), BorderLayout.CENTER);
		p3.add(getReservationsStatistics(), BorderLayout.PAGE_END);
		
		p1.add(p2);
		p1.add(p3);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		p4.add(getFinesStatistics());
		p4.add(getIDCardsStatistics());
		
		JPanel p5 = new JPanel();
		p5.setLayout(new GridLayout(2, 1, 5, 5));
		p5.add(getUsersStatistics());
		p5.add(p4);
		
		add(p1);
		add(p5);
	}
	
	private JPanel getBooksStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Books Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(6, 2, 10, 10));
		
		int disposed = AdminStats.getNumberOfDisposedBooks();
		int onloan = AdminStats.getNumberOfOnLoanBooks();
		int onshelf = AdminStats.getNumberOfOnShelfBooks();
		int reserved = AdminStats.getNumberOfReservedBooks();
		int unknown = AdminStats.getNumberOfUnknownBooks();
		int total = disposed + onloan + onshelf + reserved + unknown;
		{
			JLabel lblDisposedBooks = new JLabel("Disposed Books:");
			panel.add(lblDisposedBooks);
			
			JTextField txtDisposedBooks = new JTextField("");
			txtDisposedBooks.setColumns(6);
			txtDisposedBooks.setEditable(false);
			txtDisposedBooks.setText(Integer.toString(disposed));
			panel.add(txtDisposedBooks);
		}
		{
			JLabel lblOnLoanBooks = new JLabel("On Loan Books:");
			panel.add(lblOnLoanBooks);
			
			JTextField txtOnLoanBooks = new JTextField("");
			txtOnLoanBooks.setColumns(6);
			txtOnLoanBooks.setEditable(false);
			txtOnLoanBooks.setText(Integer.toString(onloan));
			panel.add(txtOnLoanBooks);
		}
		{
			JLabel lblOnShelfBooks = new JLabel("On Shelf Books:");
			panel.add(lblOnShelfBooks);
			
			JTextField txtOnShelfBooks = new JTextField("");
			txtOnShelfBooks.setColumns(6);
			txtOnShelfBooks.setEditable(false);
			txtOnShelfBooks.setText(Integer.toString(onshelf));
			panel.add(txtOnShelfBooks);
		}
		{
			JLabel lblReservedBooks = new JLabel("Reserved Books:");
			panel.add(lblReservedBooks);
			
			JTextField txtReservedBooks = new JTextField("");
			txtReservedBooks.setColumns(6);
			txtReservedBooks.setEditable(false);
			txtReservedBooks.setText(Integer.toString(reserved));
			panel.add(txtReservedBooks);
		}
		{
			JLabel lblUnknownBooks = new JLabel("Unknown Books:");
			panel.add(lblUnknownBooks);
			
			JTextField txtUnknownBooks = new JTextField("");
			txtUnknownBooks.setColumns(6);
			txtUnknownBooks.setEditable(false);
			txtUnknownBooks.setText(Integer.toString(unknown));
			panel.add(txtUnknownBooks);
		}
		{
			JLabel lblTotalBooks = new JLabel("Total Books:");
			panel.add(lblTotalBooks);
			
			JTextField txtTotalBooks = new JTextField("");
			txtTotalBooks.setEditable(false);
			txtTotalBooks.setColumns(6);
			txtTotalBooks.setText(Integer.toString(total));
			panel.add(txtTotalBooks);
		}
		return panel;
	}
	
	private JPanel getFinesStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Fines Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveFines();
		int inactive = AdminStats.getNumberOfInactiveFines();
		int total = active + inactive;
		{
			JLabel lblActive = new JLabel("Active Fines:");
			panel.add(lblActive);
			
			JTextField txtActive = new JTextField("");
			txtActive.setColumns(6);
			txtActive.setEditable(false);
			txtActive.setText(Integer.toString(active));
			panel.add(txtActive);
		}
		{
			JLabel lblInactive = new JLabel("Inactive Fines:");
			panel.add(lblInactive);
			
			JTextField txtInactive = new JTextField("");
			txtInactive.setEditable(false);
			txtInactive.setColumns(6);
			txtInactive.setText(Integer.toString(inactive));
			panel.add(txtInactive);
		}
		{
			JLabel lblTotal = new JLabel("Total Fines:");
			panel.add(lblTotal);
			
			JTextField txtTotal = new JTextField("");
			txtTotal.setEditable(false);
			txtTotal.setColumns(6);
			txtTotal.setText(Integer.toString(total));
			panel.add(txtTotal);
		}
		return panel;
	}
	
	private JPanel getIDCardsStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"IDCards Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveIDCards();
		int inactive = AdminStats.getNumberOfInactiveIDCards();
		int total = active + inactive;
		{
			JLabel lblActive = new JLabel("Active IDCards:");
			panel.add(lblActive);
			
			JTextField txtActive = new JTextField("");
			txtActive.setColumns(6);
			txtActive.setEditable(false);
			txtActive.setText(Integer.toString(active));
			panel.add(txtActive);
		}
		{
			JLabel lblInactive = new JLabel("Inactive IDCards:");
			panel.add(lblInactive);
			
			JTextField txtInactive = new JTextField("");
			txtInactive.setEditable(false);
			txtInactive.setColumns(6);
			txtInactive.setText(Integer.toString(inactive));
			panel.add(txtInactive);
		}
		{
			JLabel lblTotal = new JLabel("Total IDCards:");
			panel.add(lblTotal);
			
			JTextField txtTotal = new JTextField("");
			txtTotal.setEditable(false);
			txtTotal.setColumns(6);
			txtTotal.setText(Integer.toString(total));
			panel.add(txtTotal);
		}
		return panel;
	}
	
	private JPanel getLoansStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Loans Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(4, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveLoans();
		int inactive = AdminStats.getNumberOfInactiveLoans();
		int overdue = AdminStats.getNumberOfOverdueActiveLoans();
		int total = active + inactive;
		{
			JLabel lblActive = new JLabel("Active Loans:");
			panel.add(lblActive);
			
			JTextField txtActive = new JTextField("");
			txtActive.setColumns(6);
			txtActive.setEditable(false);
			txtActive.setText(Integer.toString(active));
			panel.add(txtActive);
		}
		{
			JLabel lblInactive = new JLabel("Inactive Loans:");
			panel.add(lblInactive);
			
			JTextField txtInactive = new JTextField("");
			txtInactive.setEditable(false);
			txtInactive.setColumns(6);
			txtInactive.setText(Integer.toString(inactive));
			panel.add(txtInactive);
		}
		{
			JLabel lblOverdue = new JLabel("Overdue Loans:");
			panel.add(lblOverdue);
			
			JTextField txtOverdue = new JTextField("");
			txtOverdue.setEditable(false);
			txtOverdue.setColumns(6);
			txtOverdue.setText(Integer.toString(overdue));
			panel.add(txtOverdue);
		}
		{
			JLabel lblTotal = new JLabel("Total Loans:");
			panel.add(lblTotal);
			
			JTextField txtTotal = new JTextField("");
			txtTotal.setEditable(false);
			txtTotal.setColumns(6);
			txtTotal.setText(Integer.toString(total));
			panel.add(txtTotal);
		}
		return panel;
	}
	
	private JPanel getPaymentsStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Payments Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(1, 2, 10, 10));
		
		int total = AdminStats.getNumberOfPayments();
		{
			JLabel lblTotal = new JLabel("Total Payments:");
			panel.add(lblTotal);
			
			JTextField txtTotal = new JTextField("");
			txtTotal.setEditable(false);
			txtTotal.setColumns(6);
			txtTotal.setText(Integer.toString(total));
			panel.add(txtTotal);
		}
		return panel;
	}
	
	private JPanel getReservationsStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Reservations Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveReservations();
		int inactive = AdminStats.getNumberOfInactiveReservations();
		int total = active + inactive;
		{
			JLabel lblActive = new JLabel("Active Reservations:");
			panel.add(lblActive);
			
			JTextField txtActive = new JTextField("");
			txtActive.setColumns(6);
			txtActive.setEditable(false);
			txtActive.setText(Integer.toString(active));
			panel.add(txtActive);
		}
		{
			JLabel lblInactive = new JLabel("Inactive Reservations:");
			panel.add(lblInactive);
			
			JTextField txtInactive = new JTextField("");
			txtInactive.setEditable(false);
			txtInactive.setColumns(6);
			txtInactive.setText(Integer.toString(inactive));
			panel.add(txtInactive);
		}
		{
			JLabel lblTotal = new JLabel("Total Reservations:");
			panel.add(lblTotal);
			
			JTextField txtTotal = new JTextField("");
			txtTotal.setEditable(false);
			txtTotal.setColumns(6);
			txtTotal.setText(Integer.toString(total));
			panel.add(txtTotal);
		}
		return panel;
	}
	
	private JPanel getUsersStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Users Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(1, 2, 10, 10));
		
		panel.add(getBorrowersStatistics());
		panel.add(getLibrariansStatistics());
		
		return panel;
	}
	
	private JPanel getBorrowersStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Borrowers Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveBorrowers();
		int inactive = AdminStats.getNumberOfInactiveBorrowers();
		int total = active + inactive;
		
		JLabel lblActiveBorrowers = new JLabel("Active Borrowers:");
		panel.add(lblActiveBorrowers);
		
		JTextField txtActiveBorrowers = new JTextField("");
		txtActiveBorrowers.setColumns(6);
		txtActiveBorrowers.setEditable(false);
		txtActiveBorrowers.setText(Integer.toString(active));
		panel.add(txtActiveBorrowers);
		
		JLabel lblInactiveBorrowers = new JLabel("Inactive Borrowers:");
		panel.add(lblInactiveBorrowers);
		
		JTextField txtInactiveBorrowers = new JTextField("");
		txtInactiveBorrowers.setEditable(false);
		txtInactiveBorrowers.setColumns(6);
		txtInactiveBorrowers.setText(Integer.toString(inactive));
		panel.add(txtInactiveBorrowers);
		
		JLabel lblTotalBorrowers = new JLabel("Total Borrowers:");
		panel.add(lblTotalBorrowers);
		
		JTextField txtTotalBorrowers = new JTextField("");
		txtTotalBorrowers.setEditable(false);
		txtTotalBorrowers.setColumns(6);
		txtTotalBorrowers.setText(Integer.toString(total));
		panel.add(txtTotalBorrowers);
		
		return panel;
	}
	
	private JPanel getLibrariansStatistics() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"Librarians Statistics",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		
		int active = AdminStats.getNumberOfActiveLibrarians();
		int inactive = AdminStats.getNumberOfInactiveLibrarians();
		int total = active + inactive;
		
		JLabel lblActiveLibrarians = new JLabel("Active Librarians:");
		panel.add(lblActiveLibrarians);
		
		JTextField txtActiveLibrarians = new JTextField("");
		txtActiveLibrarians.setColumns(6);
		txtActiveLibrarians.setEditable(false);
		txtActiveLibrarians.setText(Integer.toString(active));
		panel.add(txtActiveLibrarians);
		
		JLabel lblInactiveLibrarians = new JLabel("Inactive Librarians:");
		panel.add(lblInactiveLibrarians);
		
		JTextField txtInactiveLibrarians = new JTextField("");
		txtInactiveLibrarians.setEditable(false);
		txtInactiveLibrarians.setColumns(6);
		txtInactiveLibrarians.setText(Integer.toString(inactive));
		panel.add(txtInactiveLibrarians);
		
		JLabel lblTotalLibrarians = new JLabel("Total Librarians:");
		panel.add(lblTotalLibrarians);
		
		JTextField txtTotalLibrarians = new JTextField("");
		txtTotalLibrarians.setEditable(false);
		txtTotalLibrarians.setColumns(6);
		txtTotalLibrarians.setText(Integer.toString(total));
		panel.add(txtTotalLibrarians);
		
		return panel;
	}
}