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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;




import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Panel that takes input from user for book advanced search.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookAdvancedSearchPanel extends JPanel implements FilterSubject {
	
	private static final long serialVersionUID = -3091421483184806425L;
	private List<FilterObserver> observers;
	private JTextField txtTitle,
		txtID,
		txtISBN,
		txtAuthor;
	
	public BookAdvancedSearchPanel(){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		showUI();
	}
	
	private void showUI(){
		setLayout(new BorderLayout(5, 5));
		setBorder(new TitledBorder(UIManager.getBorder(
				"TitledBorder.border"),
				"Book Search",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		add(getLabelsPanel(), BorderLayout.LINE_START);
		add(getTextsPanel(), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.LINE_END);
		add(getClearButtonPanel(), BorderLayout.PAGE_END);
	}
	
	private JPanel getLabelsPanel(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(4, 1, 5, 5));
		
		panel.add(new JLabel("Search by ID"));
		panel.add(new JLabel("Search by ISBN"));
		panel.add(new JLabel("Search by Title"));
		panel.add(new JLabel("Search by Author"));
		
		return panel;
	}
	
	private JPanel getTextsPanel(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(4, 1, 5, 5));
		
		panel.add(txtID());
		panel.add(txtISBN());
		panel.add(txtTitle());
		panel.add(txtAuthor());
		
		return panel;
	}
	
	private JPanel getButtonsPanel(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(4, 1, 5, 5));
		
		panel.add(searchByIDButton());
		panel.add(searchByISBNButton());
		panel.add(searchByTitleButton());
		panel.add(searchByAuthorButton());
		
		return panel;
	}
	
	private JTextField txtTitle(){
		txtTitle = new JTextField("");
		return txtTitle;
	}
	
	private JTextField txtID(){
		txtID = new JTextField("");
		return txtID;
	}
	
	private JTextField txtISBN(){
		txtISBN = new JTextField("");
		return txtISBN;
	}
	
	private JTextField txtAuthor(){
		txtAuthor = new JTextField("");
		return txtAuthor;
	}
	
	private JButton searchByIDButton(){
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						// clearing all text fields except current
						clearTextFields("id");
						
						if(!txtID.getText().isEmpty()){
							try {
								long id = Long.parseLong(txtID.getText());
								
								try {
									List<Book> books = AdminBooks.getInstance().findBooksByID(id);
									// Request Observer update
									changeList(books);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(
											null,
											"Can't perform book search!",
											"Book Search error",
											JOptionPane.ERROR_MESSAGE);
								}
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(
										null,
										"Text in ID text field should be Number!",
										"Field Not Number error",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(
									null,
									"ID text field is Empty!",
									"Field Empty error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		return searchButton;
	}
	
	private JButton searchByISBNButton(){
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// clearing all text fields except current
							clearTextFields("isbn");
							
							List<Book> books = AdminBooks.getInstance().findBooksByISBN(txtISBN.getText());
							// Request Observer update
							changeList(books);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									null,
									"Can't perform book search!",
									"Book Search error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		return searchButton;
	}
	
	private JButton searchByTitleButton(){
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// clearing all text fields except current
							clearTextFields("title");
							
							List<Book> books = AdminBooks.getInstance().findBookByTitle(txtTitle.getText());
							// Request Observer update
							changeList(books);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									null,
									"Can't perform book search!",
									"Book Search error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		return searchButton;
	}
	
	private JButton searchByAuthorButton(){
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// clearing all text fields except current
							clearTextFields("author");
							
							List<Book> books = AdminBooks.getInstance().findBookByAuthor(txtAuthor.getText());
							// Request Observer update
							changeList(books);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									null,
									"Can't perform book search!",
									"Book Search error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
		return searchButton;
	}
	
	private JPanel getClearButtonPanel(){
		JPanel panel= new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton searchButton = new JButton("Clear Search Results");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						// clearing all text fields except current
						clearTextFields("");
						
						// Request Observer update
						changeList(null);
					}
				});
			}
		});
		panel.add(searchButton);
		
		return panel;
	}
	
	public void clearTextFields(String caller){
		if(caller.equals("id")){
			txtTitle.setText("");
			txtISBN.setText("");
			txtAuthor.setText("");
		} else if(caller.equals("isbn")){
			txtTitle.setText("");
			txtID.setText("");
			txtAuthor.setText("");
		} else if(caller.equals("title")){
			txtID.setText("");
			txtISBN.setText("");
			txtAuthor.setText("");
		} else if(caller.equals("author")){
			txtTitle.setText("");
			txtID.setText("");
			txtISBN.setText("");
		} else {
			txtTitle.setText("");
			txtID.setText("");
			txtISBN.setText("");
			txtAuthor.setText("");
		}
	}

	@Override
	public void register(FilterObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(FilterObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(Object list) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), list);
		}
  	}
	
	/**
	 * This method notifies observers about change
	 * @param list
	 */
	private void changeList(List<Book> list) {
		notifyObserver(list);
	}
}