package vkurman.jbooklibrary.gui.filters;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.gui.components.UserBasicSearchPanel;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Filter panel that allows to filter users.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserFilterPanel extends JPanel implements FilterSubject, FilterObserver {
	
	private static final long serialVersionUID = -5209849248793933405L;
	private List<FilterObserver> observers;
	private UserBasicSearchPanel userBasicSearchPanel;
	private JRadioButton allFilterButton,
		borrowerFilterButton,
		librarianFilterButton;
	
	public UserFilterPanel(){
		this("Borrower");
	}
	
	public UserFilterPanel(String userType){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		// Creating BookBasicSearchPanel and registering itself in it
		userBasicSearchPanel = new UserBasicSearchPanel(true);
		userBasicSearchPanel.register(this);
		
		showUI(userType);
	}
	
	private void showUI(String userType){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(addRadioButtons(userType));
		add(userBasicSearchPanel);
	}
	
	private JPanel addRadioButtons(String userType){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"User Filter",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		//Create the radio buttons.
	    allFilterButton = new JRadioButton("All");
	    allFilterButton.setMnemonic(KeyEvent.VK_A);
	    allFilterButton.setActionCommand("All");
	    
	    borrowerFilterButton = new JRadioButton("Borrower");
	    borrowerFilterButton.setMnemonic(KeyEvent.VK_B);
	    borrowerFilterButton.setActionCommand("Borrower");
	    
	    librarianFilterButton = new JRadioButton("Librarian");
	    librarianFilterButton.setMnemonic(KeyEvent.VK_L);
	    librarianFilterButton.setActionCommand("Librarian");
	    
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(allFilterButton);
	    group.add(borrowerFilterButton);
	    group.add(librarianFilterButton);
	
	    //Register a listener for the radio buttons.
	    allFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						notifyObserver(null);
					}
				});
			}
		});
	    
	    borrowerFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						notifyObserver("Borrower");
					}
				});
			}
		});
		
	    librarianFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						notifyObserver("Librarian");
					}
				});
			}
		});
		
	    // Checking which button to set as selected
	    this.checkSelection(userType);
	    
		panel.add(allFilterButton);
		panel.add(borrowerFilterButton);
		panel.add(librarianFilterButton);
		
		return panel;
	}
	
	private void checkSelection(String userType){
		if(userType.equals("Borrower")){
			borrowerFilterButton.setSelected(true);
		} else if(userType.equals("Librarian")){
			librarianFilterButton.setSelected(true);
		} else {
			allFilterButton.setSelected(true);
		}
	}
	
	@Override
	public void setEnabled(boolean arg){
		if(arg == true){
			allFilterButton.setEnabled(true);
			borrowerFilterButton.setEnabled(true);
			librarianFilterButton.setEnabled(true);
		} else {
			allFilterButton.setEnabled(false);
			borrowerFilterButton.setEnabled(false);
			librarianFilterButton.setEnabled(false);
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
	public void notifyObserver(Object user) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), user);
		}
  	}
	
	/**
	 * This method requests userBasicSearchPanel to reset
	 * it's values.
	 */
	public void resetSearchPanel(){
		userBasicSearchPanel.reset();
	}
	
	/**
	 * This method just passes references to this class observer from
	 * observable by this class object.
	 */
	@Override
	public void update(String className, Object arg) {
		for(FilterObserver observer : observers){
			observer.update(className, arg);
		}
	}
}